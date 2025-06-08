package com.hcltech.bookstore.Exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}