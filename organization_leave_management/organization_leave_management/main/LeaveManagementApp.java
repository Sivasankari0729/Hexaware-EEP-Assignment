package com.organization.leavemanagement.main;

import com.organization.leavemanagement.exception.*;
import com.organization.leavemanagement.model.*;
import com.organization.leavemanagement.repository.*;
import com.organization.leavemanagement.service.LeaveService;
import java.time.LocalDate;
import java.util.List;

public class LeaveManagementApp {

    private static LeaveService leaveService;

    public static void main(String[] args) {
        EmployeeRepository employeeRepo = new EmployeeRepository();
        LeaveRepository    leaveRepo    = new LeaveRepository();
        leaveService = new LeaveService(employeeRepo, leaveRepo);

        seedEmployees();

        testScenario01_ValidCasualLeave();
        testScenario02_ValidSickLeave();
        testScenario03_ValidEarnedLeave();
        testScenario04_InsufficientBalance();
        testScenario05_ExceedSickLeaveLimit();
        testScenario06_EmployeeNotFound();
        testScenario07_BlankReason();
        testScenario08_ZeroDaysRequested();
        testScenario09_NullLeaveType();
        testScenario10_AnnualLimitExceeded();
        testScenario11_LeaveHistory();
        testScenario12_LeaveBalance();
    }

    private static void seedEmployees() {
        registerSafe(new Employee("EMP001", "Alice Johnson",  15));
        registerSafe(new Employee("EMP002", "Bob Smith",       8));
        registerSafe(new Employee("EMP003", "Carol Williams", 20));
        // EMP004 intentionally absent for EmployeeNotFound test
    }

    private static void registerSafe(Employee emp) {
        try { leaveService.registerEmployee(emp); }
        catch (InvalidLeaveRequestException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void testScenario01_ValidCasualLeave() {
        applyAndDisplay(new LeaveRequest("EMP001", LeaveType.CASUAL, 3,
                "Family function", LocalDate.of(2024, 5, 10)));
    }
    private static void testScenario02_ValidSickLeave() {
        applyAndDisplay(new LeaveRequest("EMP002", LeaveType.SICK, 4,
                "Fever and cold", LocalDate.of(2024, 6, 1)));
    }
    private static void testScenario03_ValidEarnedLeave() {
        applyAndDisplay(new LeaveRequest("EMP003", LeaveType.EARNED, 7,
                "Annual vacation", LocalDate.of(2024, 7, 15)));
    }
    private static void testScenario04_InsufficientBalance() {
        applyAndDisplay(new LeaveRequest("EMP002", LeaveType.CASUAL, 10,
                "Extended vacation", LocalDate.of(2024, 8, 1)));
    }
    private static void testScenario05_ExceedSickLeaveLimit() {
        applyAndDisplay(new LeaveRequest("EMP001", LeaveType.SICK, 6,
                "Prolonged illness", LocalDate.of(2024, 9, 1)));
    }
    private static void testScenario06_EmployeeNotFound() {
        applyAndDisplay(new LeaveRequest("EMP999", LeaveType.CASUAL, 2,
                "Personal work", LocalDate.of(2024, 10, 1)));
    }
    private static void testScenario07_BlankReason() {
        applyAndDisplay(new LeaveRequest("EMP001", LeaveType.CASUAL, 1,
                "   ", LocalDate.of(2024, 10, 5)));
    }
    private static void testScenario08_ZeroDaysRequested() {
        applyAndDisplay(new LeaveRequest("EMP001", LeaveType.CASUAL, 0,
                "Half day", LocalDate.of(2024, 10, 6)));
    }
    private static void testScenario09_NullLeaveType() {
        applyAndDisplay(new LeaveRequest("EMP001", LeaveType.fromString("HOLIDAY"), 2,
                "Long weekend", LocalDate.of(2024, 10, 7)));
    }
    private static void testScenario10_AnnualLimitExceeded() {
        // 10 more (total 17) — should succeed
        applyAndDisplay(new LeaveRequest("EMP003", LeaveType.CASUAL, 10,
                "Training trip", LocalDate.of(2024, 11, 1)));
        // 5 more (total 22) — should FAIL
        applyAndDisplay(new LeaveRequest("EMP003", LeaveType.EARNED, 5,
                "Year-end holiday", LocalDate.of(2024, 12, 1)));
    }
    private static void testScenario11_LeaveHistory() {
        try {
            List<LeaveRecord> history = leaveService.getLeaveHistory("EMP001");
            history.forEach(r -> System.out.println("  HISTORY: " + r));
        } catch (EmployeeNotFoundException e) {
            System.out.println("  ERROR: " + e.getMessage());
        }
    }
    private static void testScenario12_LeaveBalance() {
        for (String id : new String[]{"EMP001","EMP002","EMP003"}) {
            try {
                System.out.printf("  %-8s Balance: %2d  Annual Used: %2d/20%n",
                        id,
                        leaveService.getLeaveBalance(id),
                        leaveService.getAnnualLeaveUsed(id));
            } catch (EmployeeNotFoundException e) {
                System.out.println("  ERROR: " + e.getMessage());
            }
        }
    }

    private static void applyAndDisplay(LeaveRequest req) {
        try {
            LeaveRecord record = leaveService.applyLeave(req);
            System.out.println("  APPROVED  : " + record);
        } catch (InvalidLeaveRequestException e) {
            System.out.println("  INVALID REQUEST      : " + e.getMessage());
        } catch (InsufficientLeaveBalanceException e) {
            System.out.println("  INSUFF. BALANCE      : " + e.getMessage());
        } catch (EmployeeNotFoundException e) {
            System.out.println("  EMPLOYEE NOT FOUND   : " + e.getMessage());
        } catch (AnnualLeaveLimitExceededException e) {
            System.out.println("  ANNUAL LIMIT EXCEEDED: " + e.getMessage());
        }
    }
}
