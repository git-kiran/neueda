package com.neueda.test.neueda.domain.exception;

public class AtmMoneylessException extends RuntimeException {
    public AtmMoneylessException(String msg) {
            super(msg);
    }
}
