package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PinInvalidExceptionTest {
    @Test
    public void testUserException() {
        try{
            throwPinInvalidException();
        } catch (Exception exception) {
            assertEquals("Pin is incorrect", exception.getMessage());
        }
    }

    private void throwPinInvalidException() throws PinInvalidException {
        throw new PinInvalidException("Pin is incorrect");
    }
}
