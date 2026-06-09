package com.organization.leavemanagement.exception;

public class AnnualLeaveLimitExceededException extends RuntimeException {
    public AnnualLeaveLimitExceededException(String message) { super(message); }
}
