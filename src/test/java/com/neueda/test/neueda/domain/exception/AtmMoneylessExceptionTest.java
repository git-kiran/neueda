package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AtmMoneylessExceptionTest {
    @Test
    public void testUserException() {
        try {
            throwAtmMoneylessExceptionTest();
        } catch (Exception exception) {
            assertEquals("Hello User, Entered amount is more than the ATM capacity, Please visit another ATM", exception.getMessage());
        }
    }

    private void throwAtmMoneylessExceptionTest() throws AtmMoneylessException {
        throw new AtmMoneylessException("Hello User, Entered amount is more than the ATM capacity, Please visit another ATM");
    }
}
