package com.poc.chainofresponsability.gateway.http;

import com.poc.chainofresponsability.domain.ExecutionContext;
import com.poc.chainofresponsability.domain.Step;
import com.poc.chainofresponsability.usecase.impl.ProcessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FlowController {

    private final ProcessHandler processHandler;

    @PostMapping("api/flow/{stepToExecute}")
    public ResponseEntity<ExecutionContext> executeFlow(@PathVariable String stepToExecute) {

        final ExecutionContext executionContext = ExecutionContext.builder()
                .build();

        final ExecutionContext processedExecutionContext = processHandler.executeProcessChainForStep(Step.valueOf(stepToExecute.toUpperCase()), executionContext);

        return ResponseEntity.ok(processedExecutionContext);
    }
}
