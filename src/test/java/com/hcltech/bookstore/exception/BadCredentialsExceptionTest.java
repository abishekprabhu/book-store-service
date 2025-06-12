package com.hcltech.bookstore.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BadCredentialsExceptionTest {

    @Test
    void testConstructorAndMessage() {
        String errorMessage = "Invalid username or password.";
        com.hcltech.bookstore.exception.BadCredentialsException exception = new BadCredentialsException(errorMessage);

        // Verify that the exception object is not null
        assertNotNull(exception);
        // Verify that the exception message is correctly set
        assertEquals(errorMessage, exception.getMessage());
    }
}
