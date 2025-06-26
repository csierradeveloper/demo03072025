package com.csierra.demo03072025.externalclients.employee;

import com.csierra.demo03072025.externalclients.employee.model.Employee;

public class EmployeeServiceClient {
    public Employee getEmployee(String employeeId) {
        if (employeeId.equals("1")) {
            return Employee.builder().build();
        } else {
            return null;
        }
    }
}
