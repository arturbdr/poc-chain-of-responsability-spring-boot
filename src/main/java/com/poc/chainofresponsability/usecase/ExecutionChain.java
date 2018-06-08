package com.poc.chainofresponsability.usecase;

import com.poc.chainofresponsability.domain.ExecutionContext;

public interface ExecutionChain {

    void executeProcess(ExecutionContext executionContext);
}
