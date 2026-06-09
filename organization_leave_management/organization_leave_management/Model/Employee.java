package com.organization.leavemanagement.model;

public class Employee {

    private String employeeId;
    private String name;
    private int leaveBalance;
    private int totalLeaveUsedThisYear;

    public Employee(String employeeId, String name, int leaveBalance) {
        this.employeeId             = employeeId;
        this.name                   = name;
        this.leaveBalance           = leaveBalance;
        this.totalLeaveUsedThisYear = 0;
    }

    public String getEmployeeId()               { return employeeId; }
    public void   setEmployeeId(String id)      { this.employeeId = id; }

    public String getName()                     { return name; }
    public void   setName(String name)          { this.name = name; }

    public int  getLeaveBalance()               { return leaveBalance; }
    public void setLeaveBalance(int balance)    { this.leaveBalance = balance; }

    public int  getTotalLeaveUsedThisYear()            { return totalLeaveUsedThisYear; }
    public void setTotalLeaveUsedThisYear(int total)   { this.totalLeaveUsedThisYear = total; }

    public void deductLeave(int days) {
        this.leaveBalance           -= days;
        this.totalLeaveUsedThisYear += days;
    }

    @Override
    public String toString() {
        return "Employee{employeeId='" + employeeId + "', name='" + name
               + "', leaveBalance=" + leaveBalance
               + ", totalLeaveUsedThisYear=" + totalLeaveUsedThisYear + '}';
    }
}
