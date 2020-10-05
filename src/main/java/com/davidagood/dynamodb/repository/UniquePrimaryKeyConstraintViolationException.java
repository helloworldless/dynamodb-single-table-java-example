package com.davidagood.dynamodb.repository;

public class UniquePrimaryKeyConstraintViolationException extends RuntimeException {
    public UniquePrimaryKeyConstraintViolationException(String message) {
        super(message);
    }
}
