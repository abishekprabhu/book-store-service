package com.hcltech.bookstore.exception;

import com.hcltech.bookstore.exception.UsernameNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsernameNotFoundExceptionTest {
    @Test
    void testConstructorAndMessage() {
        String errorMessage = "User 'testUser' not found.";
        UsernameNotFoundException exception = new UsernameNotFoundException(errorMessage);

        // Verify that the exception object is not null
        assertNotNull(exception);
        // Verify that the exception message is correctly set
        assertEquals(errorMessage, exception.getMessage());
    }
}
