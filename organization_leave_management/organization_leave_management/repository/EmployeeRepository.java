package com.organization.leavemanagement.repository;

import com.organization.leavemanagement.model.Employee;
import java.util.*;

public class EmployeeRepository {

    // HashMap chosen: O(1) lookup/insert by employeeId
    private final Map<String, Employee> employeeStore = new HashMap<>();

    public void save(Employee employee) {
        employeeStore.put(employee.getEmployeeId(), employee);
    }

    public Optional<Employee> findById(String employeeId) {
        return Optional.ofNullable(employeeStore.get(employeeId));
    }

    public Collection<Employee> findAll()          { return employeeStore.values(); }
    public boolean existsById(String employeeId)   { return employeeStore.containsKey(employeeId); }
    public int count()                             { return employeeStore.size(); }
}
