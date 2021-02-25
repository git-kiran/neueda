package com.neueda.test.neueda.domain.exception;

public class AccountNumberNotFoundException extends RuntimeException {
    public AccountNumberNotFoundException(String msg) {
        super(msg);
    }
}

