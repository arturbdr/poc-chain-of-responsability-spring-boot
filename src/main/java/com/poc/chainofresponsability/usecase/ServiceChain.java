package com.poc.chainofresponsability.usecase;

import com.poc.chainofresponsability.domain.ExecutionContext;

public interface ServiceChain {

    void executeProcess(ExecutionContext executionContext);
}
