package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.domain.Error;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.usecase.ExecutionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
public class SampleBeanB implements ExecutionChain {

    @Override
    public void executeProcess(final ExecutionContext executionContext) {
        log.info("SampleBeanB executeProcess");

        final Error error = Error.builder()
                .message("SampleBeanB")
                .field("field from Bean B")
                .code("2")
                .build();

        notNull(executionContext, "Execution Context can't be null");
        executionContext.addBean("SampleBeanB");
        executionContext.addError(error);
    }
}
