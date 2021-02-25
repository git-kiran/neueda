package com.neueda.test.neueda.domain.exception;

public class InternalErrorException extends RuntimeException {
    public InternalErrorException(String msg) {
        super(msg);
    }
}
