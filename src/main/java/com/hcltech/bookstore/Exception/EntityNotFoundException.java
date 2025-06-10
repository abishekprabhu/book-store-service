package com.hcltech.bookstore.Exception;

import java.lang.RuntimeException;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
