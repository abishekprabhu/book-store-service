package com.hcltech.bookstore.Exception;

import java.lang.RuntimeException;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String message) {
        super(message);
    }
}
