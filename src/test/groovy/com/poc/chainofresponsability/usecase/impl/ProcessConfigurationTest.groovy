package com.poc.chainofresponsability.usecase.impl

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.poc.chainofresponsability.domain.Step
import com.poc.chainofresponsability.usecase.ServiceChain
import org.assertj.core.api.ListAssert
import org.springframework.beans.factory.BeanFactory
import spock.lang.Specification

import static com.poc.chainofresponsability.domain.Step.ADDRESS
import static com.poc.chainofresponsability.domain.Step.SELECT_ADDRESS

class ProcessConfigurationTest extends Specification {

    SampleBeanA sampleBeanA = Mock(SampleBeanA.class)
    SampleBeanB sampleBeanB = Mock(SampleBeanB.class)
    SampleBeanC sampleBeanC = Mock(SampleBeanC.class)
    SampleBeanD sampleBeanD = Mock(SampleBeanD.class)

    BeanFactory beanFactory = Mock(BeanFactory.class)

    Map<Step, List<String>> steps

    ProcessConfiguration processConfiguration

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.poc.chainofresponsability.template")
    }

    def setup() {
        steps = new HashMap<>()
        processConfiguration = new ProcessConfiguration(beanFactory)
    }

    def "setup Steps Chain with step ADDRESS containing 1 step should return a map containing 1 Bean instantiated"() {
        given: "A process ADDRESS with 1 configured the process"
        steps.put(ADDRESS, Arrays.asList("sampleBeanA"))
        processConfiguration.setSteps(steps)

        when: "the application starts, it should call PostConstruct"
        beanFactory.getBean("sampleBeanA", ServiceChain.class) >> sampleBeanA
        processConfiguration.setupStepsChain()

        then: "It should load the Map with SampleBeanA (the only configured for this steps)"
        processConfiguration.getStepsBean().size() == 1
        processConfiguration.getStepsBean().get(ADDRESS).count(1)
        processConfiguration.getStepsBean().get(ADDRESS).get(0) == sampleBeanA
    }

    def "setup Steps Chain SELECT_ADDRESS containing 1 process and 3 steps should return all 3 beans instantiated ordered (B--> A --> C)"() {
        given: "A process SELECT_ADDRESS with 3 configured the process"
        steps.put(SELECT_ADDRESS, Arrays.asList("sampleBeanB", "sampleBeanA", "sampleBeanC"))
        processConfiguration.setSteps(steps)

        when: "the application starts, it should call PostConstruct"
        beanFactory.getBean("sampleBeanA", ServiceChain.class) >> sampleBeanA
        beanFactory.getBean("sampleBeanB", ServiceChain.class) >> sampleBeanB
        beanFactory.getBean("sampleBeanC", ServiceChain.class) >> sampleBeanC
        processConfiguration.setupStepsChain()

        then: "It should load the Map with the SELECT_ADDRESS and 3 beans in the order B --> A --> C"
        !processConfiguration.getStepsBean().isEmpty()
        processConfiguration.getStepsBean().get(SELECT_ADDRESS).size() == 3
        processConfiguration.getStepsBean().get(SELECT_ADDRESS) == [sampleBeanB, sampleBeanA, sampleBeanC]
    }

}
