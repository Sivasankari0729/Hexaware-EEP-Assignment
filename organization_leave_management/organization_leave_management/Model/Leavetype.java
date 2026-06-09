package com.organization.leavemanagement.model;
public enum LeaveType {
    CASUAL,
    SICK,
    EARNED;

    public static LeaveType fromString(String value) {
        if (value == null) return null;
        try {
            return LeaveType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
