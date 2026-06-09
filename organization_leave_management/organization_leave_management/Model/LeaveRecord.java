package com.organization.leavemanagement.model;

import java.time.LocalDate;

public class LeaveRecord {

    private final String    employeeId;
    private final LeaveType leaveType;
    private final int       numberOfDays;
    private final String    reason;
    private final LocalDate requestDate;
    private final LocalDate approvedDate;

    public LeaveRecord(String employeeId, LeaveType leaveType,
                       int numberOfDays, String reason, LocalDate requestDate) {
        this.employeeId   = employeeId;
        this.leaveType    = leaveType;
        this.numberOfDays = numberOfDays;
        this.reason       = reason;
        this.requestDate  = requestDate;
        this.approvedDate = LocalDate.now();
    }

    public String    getEmployeeId()   { return employeeId; }
    public LeaveType getLeaveType()    { return leaveType; }
    public int       getNumberOfDays() { return numberOfDays; }
    public String    getReason()       { return reason; }
    public LocalDate getRequestDate()  { return requestDate; }
    public LocalDate getApprovedDate() { return approvedDate; }

    @Override
    public String toString() {
        return "LeaveRecord{employeeId='" + employeeId + "', leaveType=" + leaveType
               + ", numberOfDays=" + numberOfDays + ", reason='" + reason
               + "', requestDate=" + requestDate + ", approvedDate=" + approvedDate + '}';
    }
}

