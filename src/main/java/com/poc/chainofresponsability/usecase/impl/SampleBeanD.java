package com.poc.chainofresponsability.usecase.impl;

import com.poc.chainofresponsability.domain.Error;
import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.usecase.ExecutionChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
public class SampleBeanD implements ExecutionChain {

    @Override
    public void executeProcess(final ExecutionContext executionContext) {
        log.info("SampleBeanD executeProcess");

        final Error error = Error.builder()
                .message("SampleBeanD")
                .field("field from Bean D")
                .code("4")
                .build();

        notNull(executionContext, "Execution Context can't be null");
        executionContext.addBean("SampleBeanD");
        executionContext.addError(error);
    }
}
