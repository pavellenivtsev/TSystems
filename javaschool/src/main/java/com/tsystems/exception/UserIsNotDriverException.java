package com.tsystems.exception;

public class UserIsNotDriverException extends RuntimeException {
    public UserIsNotDriverException(String message) {
        super(message);
    }
}
