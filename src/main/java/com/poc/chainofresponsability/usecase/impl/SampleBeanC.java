package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.domain.Error;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.usecase.ExecutionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
public class SampleBeanC implements ExecutionChain {

    @Override
    public void executeProcess(final ExecutionContext executionContext) {
        log.info("SampleBeanC executeProcess");

        final Error error = Error.builder()
                .message("SampleBeanC")
                .field("field from Bean C")
                .code("3")
                .build();

        notNull(executionContext, "Execution Context can't be null");
        executionContext.addBean("SampleBeanC");
        executionContext.addError(error);
    }
}
