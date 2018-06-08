package com.poc.chainofresponsability.usecase.impl

import com.poc.chainofresponsability.domain.Step
import com.poc.chainofresponsability.domain.exception.NoStepsDefinedException
import com.poc.chainofresponsability.usecase.ServiceChain
import org.springframework.beans.factory.BeanFactory
import spock.lang.Specification

import static com.poc.chainofresponsability.domain.Step.*

class ProcessConfigurationTest extends Specification {

    SampleBeanA sampleBeanA = Mock(SampleBeanA.class)
    SampleBeanB sampleBeanB = Mock(SampleBeanB.class)
    SampleBeanC sampleBeanC = Mock(SampleBeanC.class)
    SampleBeanD sampleBeanD = Mock(SampleBeanD.class)

    BeanFactory beanFactory = Mock(BeanFactory.class)

    Map<Step, List<String>> steps

    ProcessConfiguration processConfiguration

    def setup() {
        steps = new HashMap<>()
        processConfiguration = new ProcessConfiguration(beanFactory)
    }

    def "setup Steps Chain with step ADDRESS containing 1 step should return a map containing 1 Bean instantiated"() {
        given: "A process ADDRESS with 1 configured step"
        steps.put(ADDRESS, Arrays.asList("sampleBeanA"))
        processConfiguration.setSteps(steps)

        when: "the application starts, it should call PostConstruct"
        beanFactory.getBean("sampleBeanA", ServiceChain.class) >> sampleBeanA
        processConfiguration.setupStepsChain()

        then: "It should load the Map with SampleBeanA (the only configured for this steps)"
        processConfiguration.getStepsBean().size() == 1
        processConfiguration.getStepsBean().get(ADDRESS).size() == 1
        processConfiguration.getStepsBean().get(ADDRESS).get(0) == sampleBeanA
    }

    def "setup Steps Chain SELECT_ADDRESS containing 1 process and 3 steps should return all 3 beans instantiated ordered (B--> A --> C)"() {
        given: "A process SELECT_ADDRESS with 3 configured steps"
        steps.put(SELECT_ADDRESS, Arrays.asList("sampleBeanB", "sampleBeanA", "sampleBeanC"))
        processConfiguration.setSteps(steps)

        when: "the application starts, it should call PostConstruct"
        beanFactory.getBean("sampleBeanA", ServiceChain.class) >> sampleBeanA
        beanFactory.getBean("sampleBeanB", ServiceChain.class) >> sampleBeanB
        beanFactory.getBean("sampleBeanC", ServiceChain.class) >> sampleBeanC
        processConfiguration.setupStepsChain()

        then: "It should load the Map with the SELECT_ADDRESS and 3 beans in the order B --> A --> C"
        processConfiguration.getStepsBean().size() == 1
        processConfiguration.getStepsBean().get(SELECT_ADDRESS).size() == 3
        processConfiguration.getStepsBean().get(SELECT_ADDRESS) == [sampleBeanB, sampleBeanA, sampleBeanC]
    }

    def "setup Steps Chain PAYMENT_METHOD and  SELECT_PAYMENT containing 2 process and 5 steps should return both configured and in order"() {
        given: "A process PAYMENT_METHOD with 5 configured steps"
        steps.put(PAYMENT_METHOD, Arrays.asList("sampleBeanB", "sampleBeanA", "sampleBeanA", "sampleBeanA", "sampleBeanB"))
        processConfiguration.setSteps(steps)

        and: "A process SELECT_PAYMENT with 3 configured steps"
        steps.put(SELECT_PAYMENT, Arrays.asList("sampleBeanA", "sampleBeanC", "sampleBeanD"))
        processConfiguration.setSteps(steps)

        when: "the application starts, it should call PostConstruct"
        beanFactory.getBean("sampleBeanA", ServiceChain.class) >> sampleBeanA
        beanFactory.getBean("sampleBeanB", ServiceChain.class) >> sampleBeanB
        beanFactory.getBean("sampleBeanC", ServiceChain.class) >> sampleBeanC
        beanFactory.getBean("sampleBeanD", ServiceChain.class) >> sampleBeanD
        processConfiguration.setupStepsChain()

        then: "It should load the Map with the SELECT_PAYMENT and  PAYMENT_METHOD and steps configured in order"
        processConfiguration.getStepsBean().size() == 2 // SELECT_ADDRESS and PAYMENT_METHOD
        processConfiguration.getStepsBean().get(SELECT_PAYMENT).size() == 3
        processConfiguration.getStepsBean().get(SELECT_PAYMENT) == [sampleBeanA, sampleBeanC, sampleBeanD]

        processConfiguration.getStepsBean().get(PAYMENT_METHOD).size() == 5
        processConfiguration.getStepsBean().get(PAYMENT_METHOD) == [sampleBeanB, sampleBeanA, sampleBeanA, sampleBeanA, sampleBeanB]
    }

    def "It should return exception due to no steps configured"() {
        given: "No process defined"

        when: "the application starts, it should call PostConstruct"
        processConfiguration.setupStepsChain()

        then: "It should return exception"
        def exception = thrown(NoStepsDefinedException)
        exception.message == "There are no Processes defined. Its required to define it in a yml file"

    }

}
