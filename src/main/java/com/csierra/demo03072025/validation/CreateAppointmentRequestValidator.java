package com.csierra.demo03072025.validation;

import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.externalclients.employee.EmployeeServiceRestClient;
import com.csierra.demo03072025.externalclients.employee.model.Employee;
import com.csierra.demo03072025.externalclients.office.OfficeServiceRestClient;
import com.csierra.demo03072025.externalclients.office.model.Office;
import com.csierra.demo03072025.externalclients.property.PropertyServiceRestClient;
import com.csierra.demo03072025.externalclients.property.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CreateAppointmentRequestValidator {
    @Autowired
    private PropertyServiceRestClient propertyServiceRestClient;
    @Autowired
    private EmployeeServiceRestClient employeeServiceRestClient;
    @Autowired
    private OfficeServiceRestClient officeServiceRestClient;

    public void validateRequest(CreateAppointmentRequest createAppointmentRequest) {
        Map<String, String> errors = new HashMap<>();

        validatePropertyExists(createAppointmentRequest.getPropertyId(), errors);
        validateEmployeeExists(createAppointmentRequest.getAgentId(), errors);
        validateOfficeExists(createAppointmentRequest.getOfficeId(), errors);
        validateAppointmentInFuture(createAppointmentRequest.getAppointmentTime(), errors);

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    private void validatePropertyExists(String propertyId, Map<String, String> errors) {
        ResponseEntity<Property> propertySearchResponse = propertyServiceRestClient.getProperty(propertyId);

        if (!propertySearchResponse.getStatusCode().is2xxSuccessful()) {
            errors.put("propertyId", "provided propertyId does not correspond to an existing insurable property");
        }
    }

    private void validateEmployeeExists(String employeeId, Map<String, String> errors) {
        ResponseEntity<Employee> employeeSearchResponse = employeeServiceRestClient.getEmployee(employeeId);

        if (!employeeSearchResponse.getStatusCode().is2xxSuccessful()) {
            errors.put("employeeId", "provided employeeId does not correspond to a valid employee to serve as insurance agent");
        }
    }

    private void validateOfficeExists(String officeId, Map<String, String> errors) {
        ResponseEntity<Office> officeSearchResponse = officeServiceRestClient.getOffice(officeId);

        if (!officeSearchResponse.getStatusCode().is2xxSuccessful()) {
            errors.put("officeId", "provided officeId does not correspond to an existing location for the appointment to take place in");
        }
    }

    private void validateAppointmentInFuture(OffsetDateTime appointmentTime, Map<String, String> errors) {
        if (appointmentTime.isBefore(OffsetDateTime.now())) {
            errors.put("appointmentTime", "provided appointment time is in the past, cannot create retroactive appointments");
        }
    }
}
