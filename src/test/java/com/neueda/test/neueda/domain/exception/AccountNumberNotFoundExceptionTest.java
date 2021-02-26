package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountNumberNotFoundExceptionTest {
    @Test
    public void testUserException() {
        try {
            throwAccountNumberNotFoundException();
        } catch (Exception exception) {
            assertEquals("AccountNumber not found", exception.getMessage());
        }
    }

    private void throwAccountNumberNotFoundException() throws AccountNumberNotFoundException {
        throw new AccountNumberNotFoundException("AccountNumber not found");
    }
}
