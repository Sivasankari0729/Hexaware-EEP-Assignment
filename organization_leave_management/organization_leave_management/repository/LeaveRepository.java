package com.organization.leavemanagement.repository;

import com.organization.leavemanagement.model.LeaveRecord;
import java.util.*;
import java.util.stream.Collectors;

public class LeaveRepository {

    // ArrayList: records are append-only; sequential scan is acceptable
    private final List<LeaveRecord> leaveRecords = new ArrayList<>();

    public void save(LeaveRecord record) { leaveRecords.add(record); }

    public List<LeaveRecord> findByEmployeeId(String employeeId) {
        return leaveRecords.stream()
                .filter(r -> r.getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public List<LeaveRecord> findAll() { return new ArrayList<>(leaveRecords); }
    public int count()                 { return leaveRecords.size(); }
}
