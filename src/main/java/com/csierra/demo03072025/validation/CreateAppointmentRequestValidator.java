package com.csierra.demo03072025.validation;

import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.externalclients.employee.EmployeeServiceClient;
import com.csierra.demo03072025.externalclients.employee.model.Employee;
import com.csierra.demo03072025.externalclients.office.OfficeServiceClient;
import com.csierra.demo03072025.externalclients.office.model.Office;
import com.csierra.demo03072025.externalclients.property.PropertyServiceClient;
import com.csierra.demo03072025.externalclients.property.model.Property;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class CreateAppointmentRequestValidator {
    PropertyServiceClient propertyServiceClient;
    EmployeeServiceClient employeeServiceClient;
    OfficeServiceClient officeServiceClient;

    public void validateRequest(CreateAppointmentRequest createAppointmentRequest) {
        validatePropertyExists(createAppointmentRequest.getPropertyId());
        validateEmployeeExists(createAppointmentRequest.getAgentId());
        validateOfficeExists(createAppointmentRequest.getOfficeId());
        validateAppointmentInFuture(createAppointmentRequest.getAppointmentTime());
    }

    private void validatePropertyExists(String propertyId) {
        Property apptProperty = propertyServiceClient.getProperty(propertyId);

        if (apptProperty == null) {
            //throw
        }
    }

    private void validateEmployeeExists(String employeeId) {
        Employee apptEmployee = employeeServiceClient.getEmployee(employeeId);

        if (apptEmployee == null) {
            //throw
        }
    }

    private void validateOfficeExists(String officeId) {
        Office apptOffice = officeServiceClient.getOffice(officeId);

        if (apptOffice == null) {
            //throw
        }
    }

    private void validateAppointmentInFuture(OffsetDateTime appointmentTime) {
        if (appointmentTime.isAfter(OffsetDateTime.now())) {
            //throw
        }
    }
}
