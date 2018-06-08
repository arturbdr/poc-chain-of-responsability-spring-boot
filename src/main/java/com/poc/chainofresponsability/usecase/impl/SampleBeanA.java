package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.domain.Error;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.usecase.ExecutionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
public class SampleBeanA implements ExecutionChain {

    @Override
    public void executeProcess(final ExecutionContext executionContext) {
        log.info("SampleBeanA executeProcess");

        final Error error = Error.builder()
                .message("SampleBeanA")
                .field("field from Bean A")
                .code("1")
                .build();

        notNull(executionContext, "Execution Context can't be null");
        executionContext.addBean("SampleBeanA");
        executionContext.addError(error);
    }
}
