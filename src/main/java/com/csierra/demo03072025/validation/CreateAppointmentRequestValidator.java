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

@Component
public class CreateAppointmentRequestValidator {
    @Autowired
    private PropertyServiceRestClient propertyServiceRestClient;
    @Autowired
    private EmployeeServiceRestClient employeeServiceRestClient;
    @Autowired
    private OfficeServiceRestClient officeServiceRestClient;

    //TODO: instead of throwing at first missing field, check all and return a list of errors
    public void validateRequest(CreateAppointmentRequest createAppointmentRequest) {
        validatePropertyExists(createAppointmentRequest.getPropertyId());
        validateEmployeeExists(createAppointmentRequest.getAgentId());
        validateOfficeExists(createAppointmentRequest.getOfficeId());
        validateAppointmentInFuture(createAppointmentRequest.getAppointmentTime());
    }

    private void validatePropertyExists(String propertyId) {
        ResponseEntity<Property> propertySearchResponse = propertyServiceRestClient.getProperty(propertyId);

        if (!propertySearchResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("provided propertyId does not correspond to an existing insurable property");
        }
    }

    private void validateEmployeeExists(String employeeId) {
        ResponseEntity<Employee> employeeSearchResponse = employeeServiceRestClient.getEmployee(employeeId);

        if (!employeeSearchResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("provided employeeId does not correspond to an existing employee to serve as insurance agent");
        }
    }

    private void validateOfficeExists(String officeId) {
        ResponseEntity<Office> officeSearchResponse = officeServiceRestClient.getOffice(officeId);

        if (!officeSearchResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("provided officeId does not correspond to an existing location for the appointment to take place in");
        }
    }

    private void validateAppointmentInFuture(OffsetDateTime appointmentTime) {
        if (appointmentTime.isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("provided appointment time is in the past, cannot create retroactive appointments");
        }
    }
}
