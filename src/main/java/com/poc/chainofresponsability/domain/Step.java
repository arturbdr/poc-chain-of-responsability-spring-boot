package com.poc.chainofresponsability.domain;

public enum Step {
    ADDRESS,
    SELECT_ADDRESS,
    SHIPPING,
    SELECT_PAYMENT,
    PAYMENT_METHOD,
}