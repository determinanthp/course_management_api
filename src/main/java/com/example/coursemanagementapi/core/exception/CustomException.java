package com.example.coursemanagementapi.core.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
