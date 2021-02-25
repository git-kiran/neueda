package com.neueda.test.neueda.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DispenseExactAmountExceptionTest {
    @Test
    public void testUserException() {
        try{
            throwDispenseExactAmountExceptionTest();
        } catch (Exception exception) {
            assertEquals("Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine", exception.getMessage());
        }
    }

    private void throwDispenseExactAmountExceptionTest() throws DispenseExactAmountException {
        throw new DispenseExactAmountException("Hello User, Sorry!!! we could not dispense the exact amount you requested due to shortage of notes in the ATM machine");
    }
}
