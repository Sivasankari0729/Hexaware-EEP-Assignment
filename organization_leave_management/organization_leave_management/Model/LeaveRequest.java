package com.organization.leavemanagement.model;

import java.time.LocalDate;

public class LeaveRequest {

    private String    employeeId;
    private LeaveType leaveType;
    private int       numberOfDays;
    private String    reason;
    private LocalDate requestDate;

    public LeaveRequest(String employeeId, LeaveType leaveType,
                        int numberOfDays, String reason, LocalDate requestDate) {
        this.employeeId   = employeeId;
        this.leaveType    = leaveType;
        this.numberOfDays = numberOfDays;
        this.reason       = reason;
        this.requestDate  = requestDate;
    }

    public String    getEmployeeId()   { return employeeId; }
    public LeaveType getLeaveType()    { return leaveType; }
    public int       getNumberOfDays() { return numberOfDays; }
    public String    getReason()       { return reason; }
    public LocalDate getRequestDate()  { return requestDate; }

    public void setEmployeeId(String v)        { this.employeeId   = v; }
    public void setLeaveType(LeaveType v)      { this.leaveType    = v; }
    public void setNumberOfDays(int v)         { this.numberOfDays = v; }
    public void setReason(String v)            { this.reason       = v; }
    public void setRequestDate(LocalDate v)    { this.requestDate  = v; }

    @Override
    public String toString() {
        return "LeaveRequest{employeeId='" + employeeId + "', leaveType=" + leaveType
               + ", numberOfDays=" + numberOfDays + ", reason='" + reason
               + "', requestDate=" + requestDate + '}';
    }
}
