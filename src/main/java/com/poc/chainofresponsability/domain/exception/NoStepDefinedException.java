package com.poc.chainofresponsability.domain.exception;

public class NoStepDefinedException extends RuntimeException {

    public NoStepDefinedException(String message) {
        super(message);
    }
}
