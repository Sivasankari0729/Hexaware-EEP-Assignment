package com.organization.leavemanagement.service;

import com.organization.leavemanagement.exception.*;
import com.organization.leavemanagement.model.*;
import com.organization.leavemanagement.repository.*;
import com.organization.leavemanagement.util.ValidationUtil;
import java.util.List;

public class LeaveService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRepository    leaveRepository;

    public LeaveService(EmployeeRepository employeeRepository,
                        LeaveRepository leaveRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRepository    = leaveRepository;
    }

    public LeaveRecord applyLeave(LeaveRequest request) {

        // Step 1 — Field-level validation
        ValidationUtil.validateString(request.getEmployeeId(), "Employee ID");
        ValidationUtil.validateLeaveType(request.getLeaveType());
        ValidationUtil.validatePositiveInt(request.getNumberOfDays(), "Number of days");
        ValidationUtil.validateString(request.getReason(), "Reason");
        ValidationUtil.validateSickLeaveLimit(request.getLeaveType(), request.getNumberOfDays());

        // Step 2 — Employee must exist
        Employee employee = employeeRepository
                .findById(request.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with ID: " + request.getEmployeeId()));

        // Step 3 — Business rules
        if (request.getNumberOfDays() > employee.getLeaveBalance()) {
            throw new InsufficientLeaveBalanceException(
                    "Insufficient leave balance for employee [" + employee.getName() + "]. "
                    + "Requested: " + request.getNumberOfDays()
                    + ", Available: " + employee.getLeaveBalance());
        }

        ValidationUtil.validateAnnualLimit(
                employee.getTotalLeaveUsedThisYear(),
                request.getNumberOfDays(),
                employee.getEmployeeId());

        // Step 4 — Persist and deduct
        employee.deductLeave(request.getNumberOfDays());
        employeeRepository.save(employee);

        LeaveRecord record = new LeaveRecord(
                request.getEmployeeId(), request.getLeaveType(),
                request.getNumberOfDays(), request.getReason(), request.getRequestDate());

        leaveRepository.save(record);
        return record;
    }

    public List<LeaveRecord> getLeaveHistory(String employeeId) {
        ValidationUtil.validateString(employeeId, "Employee ID");
        if (!employeeRepository.existsById(employeeId))
            throw new EmployeeNotFoundException("Employee not found: " + employeeId);
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public int getLeaveBalance(String employeeId) {
        ValidationUtil.validateString(employeeId, "Employee ID");
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found: " + employeeId))
                .getLeaveBalance();
    }

    public int getAnnualLeaveUsed(String employeeId) {
        ValidationUtil.validateString(employeeId, "Employee ID");
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found: " + employeeId))
                .getTotalLeaveUsedThisYear();
    }

    public void registerEmployee(Employee employee) {
        ValidationUtil.validateString(employee.getEmployeeId(), "Employee ID");
        ValidationUtil.validateString(employee.getName(), "Employee Name");
        ValidationUtil.validatePositiveInt(employee.getLeaveBalance(), "Leave Balance");
        employeeRepository.save(employee);
    }
}

