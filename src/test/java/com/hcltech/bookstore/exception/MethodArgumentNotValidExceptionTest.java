package com.hcltech.bookstore.exception;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MethodArgumentNotValidExceptionTest {

    @Test
    void testConstructorAndMessage() {
        String errorMessage = "Validation failed for argument";
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(errorMessage);

        // Verify that the exception message is correctly set
        assertEquals(errorMessage, exception.getMessage());
        assertEquals(errorMessage, exception.getMessage()); // Direct access for verification if needed, though not recommended
    }

    @Test
    void testGetBindingResultThrowsUnsupportedOperationException() {
        String errorMessage = "Validation failed";
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(errorMessage);

        UnsupportedOperationException thrown = assertThrows(UnsupportedOperationException.class, exception::getBindingResult);

        assertEquals("BindingResult is not available in this context.", thrown.getMessage());
    }

}
