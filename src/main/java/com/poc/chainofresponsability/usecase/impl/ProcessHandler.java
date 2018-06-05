package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.config.ProcessConfiguration;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.domain.Step;
import com.poc.chainofresponsability.usecase.ServiceChain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
@RequiredArgsConstructor
public class ProcessHandler {

    private final ProcessConfiguration processConfiguration;

    public ExecutionContext executeProcessChainForStep(final Step step, final ExecutionContext executionContext) {
        notNull(step, "the Step must be defined");
        notNull(executionContext, "the execution context cant be null");

        final List<ServiceChain> beansToExecute = processConfiguration
                .getStepsBean()
                .get(step);

        beansToExecute.forEach(processToBeExecuted -> processToBeExecuted.executeProcess(executionContext));
        return executionContext;
    }

}
