package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountBalanceExceptionTest {
    @Test
    public void testUserException() {
        try {
            throwAccountBalanceException();
        } catch (Exception exception) {
            assertEquals("Hello User, the amount you entered is more than the actual balance", exception.getMessage());
        }
    }

    private void throwAccountBalanceException() throws AccountBalanceException {
        throw new AccountBalanceException("Hello User, the amount you entered is more than the actual balance");
    }
}
