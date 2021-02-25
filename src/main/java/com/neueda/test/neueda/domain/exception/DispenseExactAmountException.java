package com.neueda.test.neueda.domain.exception;

public class DispenseExactAmountException extends RuntimeException {
    public DispenseExactAmountException(String msg) {
        super(msg);
    }
}