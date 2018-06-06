package com.poc.chainofresponsability.usecase.impl

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.poc.chainofresponsability.domain.Step
import com.poc.chainofresponsability.usecase.ServiceChain
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.slf4j.Logger
import org.springframework.beans.factory.BeanFactory
import spock.lang.Specification

class ProcessConfigurationTest extends Specification {

    @Mock
    Map<Step, List<String>> steps

    @Mock
    Map<Step, List<ServiceChain>> stepsBean

    @Mock
    BeanFactory beanFactory

    @Mock
    Logger log

    @InjectMocks
    ProcessConfiguration processConfiguration

    def setupSpec() {
        FixtureFactoryLoader.loadTemplates("com.poc.chainofresponsability.template")
    }

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test setup Steps Chain - 1 process and 1 step"() {
        given "A process with 1 configured the process"

        List<String> listOfSteps = Arrays.asList("sampleBeanA")
        steps = new HashMap<>()
        steps.put(Step.ADDRESS, _)


        processConfiguration.setupStepsChain()



        then
        false
    }

    def "test get Steps Bean"() {
        when:
        Map<Step, List<ServiceChain>> result = processConfiguration.getStepsBean()

        then:
        result == [(Step.ADDRESS): [new SampleBeanB()]]
    }

    def "test set Steps"() {
        when:
        processConfiguration.setSteps([(Step.ADDRESS): ["String"]])

        then:
        false//todo - validate something
    }
}
