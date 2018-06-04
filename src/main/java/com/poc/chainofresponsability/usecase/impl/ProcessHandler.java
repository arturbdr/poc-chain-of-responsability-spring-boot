package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.configuration.ProcessConfigurationProperties;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.domain.Step;
import com.poc.chainofresponsability.usecase.ServiceChain;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class ProcessHandler {

    private final ProcessConfigurationProperties processConfigurationProperties;
    private final BeanFactory beanFactory;

    public ExecutionContext executeProcessChainForStep(final Step step, final ExecutionContext executionContext) {
        notNull(step, "the Step must be defined");

        List<String> beansToExecute = processConfigurationProperties.getSteps()
                .get(step);

        beansToExecute.forEach(processToBeExecuted -> {
            final ServiceChain beanToBeExecuted = (ServiceChain) beanFactory.getBean(processToBeExecuted);
            beanToBeExecuted.executeProcess(executionContext);
        });

        return executionContext;
    }


}
