package com.csierra.demo03072025.validation;

import com.csierra.demo03072025.controller.model.AppointmentUser;
import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.externalclients.employee.EmployeeServiceRestClient;
import com.csierra.demo03072025.externalclients.employee.model.Employee;
import com.csierra.demo03072025.externalclients.office.OfficeServiceRestClient;
import com.csierra.demo03072025.externalclients.office.model.Office;
import com.csierra.demo03072025.externalclients.property.PropertyServiceRestClient;
import com.csierra.demo03072025.externalclients.property.model.Property;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

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
    AppointmentUser appointmentUser;
    @Mock
    Property property;
    @Mock
    Employee employee;
    @Mock
    Office office;

    @Mock
    PropertyServiceRestClient propertyServiceRestClient;
    @Mock
    EmployeeServiceRestClient employeeServiceRestClient;
    @Mock
    OfficeServiceRestClient officeServiceRestClient;

    @InjectMocks
    CreateAppointmentRequestValidator createAppointmentRequestValidator;

    private CreateAppointmentRequest createAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .user(appointmentUser)
                .propertyId(PROPERTY_ID)
                .agentId(AGENT_ID)
                .officeId(OFFICE_ID)
                .appointmentTime(appointmentTime)
                .build();
    }

    @Test
    void testValidateRequest_missingProperty() {
        when(propertyServiceRestClient.getProperty(PROPERTY_ID)).thenReturn(buildNotFoundResponse());

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    private ResponseEntity buildNotFoundResponse() {
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
    }

    @Test
    void testValidateRequest_missingEmployee() {
        when(propertyServiceRestClient.getProperty(PROPERTY_ID)).thenReturn(ResponseEntity.ok(property));
        when(employeeServiceRestClient.getEmployee(AGENT_ID)).thenReturn(buildNotFoundResponse());

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_missingOffice() {
        when(propertyServiceRestClient.getProperty(PROPERTY_ID)).thenReturn(ResponseEntity.ok(property));
        when(employeeServiceRestClient.getEmployee(AGENT_ID)).thenReturn(ResponseEntity.ok(employee));
        when(officeServiceRestClient.getOffice(OFFICE_ID)).thenReturn(buildNotFoundResponse());

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_appointmentSetInPast() {
        when(propertyServiceRestClient.getProperty(PROPERTY_ID)).thenReturn(ResponseEntity.ok(property));
        when(employeeServiceRestClient.getEmployee(AGENT_ID)).thenReturn(ResponseEntity.ok(employee));
        when(officeServiceRestClient.getOffice(OFFICE_ID)).thenReturn(ResponseEntity.ok(office));
        when(appointmentTime.isAfter(any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> createAppointmentRequestValidator.validateRequest(createAppointmentRequest()));
    }

    @Test
    void testValidateRequest_happyPath() {
        when(propertyServiceRestClient.getProperty(PROPERTY_ID)).thenReturn(ResponseEntity.ok(property));
        when(employeeServiceRestClient.getEmployee(AGENT_ID)).thenReturn(ResponseEntity.ok(employee));
        when(officeServiceRestClient.getOffice(OFFICE_ID)).thenReturn(ResponseEntity.ok(office));
        when(appointmentTime.isAfter(any())).thenReturn(false);

        createAppointmentRequestValidator.validateRequest(createAppointmentRequest());

        verify(propertyServiceRestClient).getProperty(PROPERTY_ID);
        verify(employeeServiceRestClient).getEmployee(AGENT_ID);
        verify(officeServiceRestClient).getOffice(OFFICE_ID);
    }


}