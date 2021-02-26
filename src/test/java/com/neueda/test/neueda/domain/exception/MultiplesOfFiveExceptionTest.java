package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultiplesOfFiveExceptionTest {
    @Test
    public void testUserException() {
        try {
            throwMultiplesOfFiveException();
        } catch (Exception exception) {
            assertEquals("Hello User, Please enter withdrawal amount in multiples of five", exception.getMessage());
        }
    }

    private void throwMultiplesOfFiveException() throws MultiplesOfFiveException {
        throw new MultiplesOfFiveException("Hello User, Please enter withdrawal amount in multiples of five");
    }
}
