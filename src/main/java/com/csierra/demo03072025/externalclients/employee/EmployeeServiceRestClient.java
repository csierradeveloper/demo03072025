package com.csierra.demo03072025.externalclients.employee;

import com.csierra.demo03072025.externalclients.employee.model.Employee;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeServiceRestClient {

    //Equivalent to GET employeeServiceBaseUrl/rest/employee/{employeeId}
    public ResponseEntity<Employee> getEmployee(String employeeId) {
        if (employeeId.equals("1")) {
            return ResponseEntity.ok(Employee.builder().build());
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
}
