package com.poc.chainofresponsability.domain.exception;

public class NoStepsDefinedException extends RuntimeException {

    public NoStepsDefinedException(String message) {
        super(message);
    }
}
