package com.csierra.demo03072025.validation;

import com.csierra.demo03072025.controller.model.AppointmentUser;
import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.externalclients.employee.EmployeeServiceClient;
import com.csierra.demo03072025.externalclients.employee.model.Employee;
import com.csierra.demo03072025.externalclients.office.OfficeServiceClient;
import com.csierra.demo03072025.externalclients.office.model.Office;
import com.csierra.demo03072025.externalclients.property.PropertyServiceClient;
import com.csierra.demo03072025.externalclients.property.model.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateAppointmentRequestValidatorTest {
    private static final String PROPERTY_ID = "propertyId";
    private static final String AGENT_ID = "agentId";
    private static final String OFFICE_ID = "officeId";

    @Mock
    OffsetDateTime appointmentTime;

    @Mock
    PropertyServiceClient propertyServiceClient;
    @Mock
    EmployeeServiceClient employeeServiceClient;
    @Mock
    OfficeServiceClient officeServiceClient;

    @InjectMocks
    CreateAppointmentRequestValidator createAppointmentRequestValidator;

    private CreateAppointmentRequest createAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .user(AppointmentUser.builder().build())
                .propertyId(PROPERTY_ID)
                .agentId(AGENT_ID)
                .officeId(OFFICE_ID)
                .appointmentTime(appointmentTime)
                .build();
    }

    @Test
    void testValidateRequest_missingProperty() {
        when(propertyServiceClient.getProperty(PROPERTY_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_missingEmployee() {
        when(propertyServiceClient.getProperty(PROPERTY_ID)).thenReturn(Property.builder().build());
        when(employeeServiceClient.getEmployee(AGENT_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_missingOffice() {
        when(propertyServiceClient.getProperty(PROPERTY_ID)).thenReturn(Property.builder().build());
        when(employeeServiceClient.getEmployee(AGENT_ID)).thenReturn(Employee.builder().build());
        when(officeServiceClient.getOffice(OFFICE_ID)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_appointmentSetInPast() {
        when(propertyServiceClient.getProperty(PROPERTY_ID)).thenReturn(Property.builder().build());
        when(employeeServiceClient.getEmployee(AGENT_ID)).thenReturn(Employee.builder().build());
        when(officeServiceClient.getOffice(OFFICE_ID)).thenReturn(Office.builder().build());
        when(appointmentTime.isAfter(any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_happyPath() {
        when(propertyServiceClient.getProperty(PROPERTY_ID)).thenReturn(Property.builder().build());
        when(employeeServiceClient.getEmployee(AGENT_ID)).thenReturn(Employee.builder().build());
        when(officeServiceClient.getOffice(OFFICE_ID)).thenReturn(Office.builder().build());
        when(appointmentTime.isAfter(any())).thenReturn(false);

        createAppointmentRequestValidator.validateRequest(createAppointmentRequest());

        verify(propertyServiceClient).getProperty(PROPERTY_ID);
        verify(employeeServiceClient).getEmployee(AGENT_ID);
        verify(officeServiceClient).getOffice(OFFICE_ID);
    }


}