package com.csierra.demo03072025.controller;

import com.csierra.demo03072025.service.AppointmentService;
import com.csierra.demo03072025.persistence.Appointment;
import com.csierra.demo03072025.persistence.AppointmentState;
import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.controller.model.CreateAppointmentResponse;
import com.csierra.demo03072025.service.NotificationService;
import com.csierra.demo03072025.service.UserService;
import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.validation.CreateAppointmentRequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
@Slf4j
class AppointmentController {

    @Autowired
    private CreateAppointmentRequestValidator createAppointmentRequestValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private NotificationService notificationService;

    //TODO: should be a post, or a put?
    @PostMapping("/appointment/create")
    ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {

        log.debug("Received createAppointmentRequest: " + createAppointmentRequest);

        createAppointmentRequestValidator.validateRequest(createAppointmentRequest);

        log.debug("Request passed validation");

        User user = userService.getUser(createAppointmentRequest.getUser());
        log.debug("User for the appointment: " + user);

        Appointment appointment = appointmentService.getAppointment(user, createAppointmentRequest);
        log.debug("Appointment for this request: " + appointment);

        if (!appointment.getAppointmentState().equals(AppointmentState.CREATED)) {
            log.debug("Appointment already exists and is not in a state to need further notification, returning early");
            throw new IllegalStateException("Appointment already exists and user has already been notified");
        }

        //Attempt to deliver notification to client, report result if request fails
        if (!notificationService.sendAppointmentNotification(user, appointment)) {
            throw new InternalError("Unable to deliver notification");
        }

        //NOTE: We'd want appointmentService to update the status through the repository class, doing this as shorthand
        appointment.setAppointmentState(AppointmentState.NOTIFIED);

        return ResponseEntity.ok().body(CreateAppointmentResponse.builder()
                .appointmentId(appointment.getId())
                .appointmentStatus(appointment.getAppointmentState())
                .build());
    }
}
