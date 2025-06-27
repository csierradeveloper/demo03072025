package com.csierra.demo03072025.controller;

import com.csierra.demo03072025.service.AppointmentService;
import com.csierra.demo03072025.controller.model.AppointmentUser;
import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.service.UserService;
import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.validation.CreateAppointmentRequestValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    private static final String PROPERTY_ID = "propertyId";
    private static final String AGENT_ID = "agentId";
    private static final String OFFICE_ID = "officeId";
    private static final String USER_NAME = "userName";

    @Mock
    OffsetDateTime appointmentTime;
    @Mock
    AppointmentUser appointmentUser;
    @Mock
    User user;

    @Mock
    CreateAppointmentRequestValidator validator;
    @Mock
    UserService userService;
    @Mock
    AppointmentService appointmentService;

    @InjectMocks
    AppointmentController appointmentController;

    private CreateAppointmentRequest buildAppointmentRequest() {
        return CreateAppointmentRequest.builder()
                .user(appointmentUser)
                .propertyId(PROPERTY_ID)
                .agentId(AGENT_ID)
                .officeId(OFFICE_ID)
                .appointmentTime(appointmentTime)
                .build();
    }

    @Test
    void testCreateAppointment_requestFailsValidation() {
        doThrow(new IllegalArgumentException()).when(validator).validateRequest(any());

        assertThrows(IllegalArgumentException.class, () -> appointmentController.createAppointment(buildAppointmentRequest()));
    }

    @Test
    void testCreateAppointment_failureInUserCreation() {
        when(userService.getUser(any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> appointmentController.createAppointment(buildAppointmentRequest()));
    }

    @Test
    void testCreateAppointment_failureInAppointmentCreation() {
        when(userService.getUser(any())).thenReturn(user);
        when(appointmentService.getAppointment(any(), any())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> appointmentController.createAppointment(buildAppointmentRequest()));
    }

}