package com.organization.leavemanagement.util;

import com.organization.leavemanagement.exception.AnnualLeaveLimitExceededException;
import com.organization.leavemanagement.exception.InvalidLeaveRequestException;
import com.organization.leavemanagement.model.LeaveType;

public final class ValidationUtil {

    private ValidationUtil() {}

    public static final int MAX_SICK_CONSECUTIVE_DAYS = 5;
    public static final int MAX_ANNUAL_LEAVE_DAYS     = 20;

    // String — null-safe trim + blank check
    public static void validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidLeaveRequestException(
                    fieldName + " cannot be null or blank.");
        }
    }

    // Numeric — must be > 0
    public static void validatePositiveInt(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidLeaveRequestException(
                    fieldName + " must be greater than 0. Provided: " + value);
        }
    }

    // Enum — null means unresolvable string was supplied
    public static void validateLeaveType(LeaveType leaveType) {
        if (leaveType == null) {
            throw new InvalidLeaveRequestException(
                    "LeaveType is invalid. Accepted values: CASUAL, SICK, EARNED.");
        }
    }

    // Business rule — max 5 consecutive sick days
    public static void validateSickLeaveLimit(LeaveType leaveType, int days) {
        if (LeaveType.SICK.equals(leaveType) && days > MAX_SICK_CONSECUTIVE_DAYS) {
            throw new InvalidLeaveRequestException(
                    "SICK leave cannot exceed " + MAX_SICK_CONSECUTIVE_DAYS
                    + " consecutive days. Requested: " + days);
        }
    }

    // Advanced rule — max 20 days per year
    public static void validateAnnualLimit(int alreadyUsed, int requested, String employeeId) {
        if (alreadyUsed + requested > MAX_ANNUAL_LEAVE_DAYS) {
            int remaining = MAX_ANNUAL_LEAVE_DAYS - alreadyUsed;
            throw new AnnualLeaveLimitExceededException(
                    "Employee [" + employeeId + "] would exceed the annual limit of "
                    + MAX_ANNUAL_LEAVE_DAYS + " days. Already used: " + alreadyUsed
                    + ", Requested: " + requested + ", Remaining quota: " + remaining);
        }
    }
}
