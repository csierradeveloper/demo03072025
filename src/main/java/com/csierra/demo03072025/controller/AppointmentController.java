package com.csierra.demo03072025.controller;

import com.csierra.demo03072025.appointment.AppointmentService;
import com.csierra.demo03072025.appointment.model.Appointment;
import com.csierra.demo03072025.appointment.model.SearchAppointmentRequest;
import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.controller.model.CreateAppointmentResponse;
import com.csierra.demo03072025.externalclients.user.UserRestClient;
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
    private UserRestClient userRestClient;
    @Autowired
    private AppointmentService appointmentService;

    //TODO: should be a post, or a put?
    @PostMapping("/appointment/create")
    ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {

        //TODO: either remove logs or change most of them to debug

        log.info("Received createAppointmentRequest: " + createAppointmentRequest);

        createAppointmentRequestValidator.validateRequest(createAppointmentRequest);

        log.info("Request passed validation");

        User user = userRestClient.findMatchingUser(createAppointmentRequest.getUser());
        if (user == null) {
            log.info("Creating a user for this appointment");
            user = userRestClient.createUser(createAppointmentRequest.getUser());
        }
        log.info("User for the appointment: " + user);

        //Search for an appointment matching what you're trying to make. If one already exists in anything other than created state, exit early
        SearchAppointmentRequest searchAppointmentRequest = SearchAppointmentRequest.builder()
                .userId(user.getId())
                .propertyId(createAppointmentRequest.getPropertyId())
                .appointmentTime(createAppointmentRequest.getAppointmentTime())
                .officeId(createAppointmentRequest.getOfficeId())
                .agentId(createAppointmentRequest.getAgentId())
                .build();
        Appointment appointment = appointmentService.findExistingAppointment(searchAppointmentRequest);
        if (appointment == null) {
            log.info("Creating a new appointment");
            appointment = appointmentService.createAppointment(searchAppointmentRequest);
        }

        log.info("Appointment for this request: " + appointment);

        //Send request to notification service, get response back

        //If response is successful, update appointment status

        //return 200 (or appropriate response code)

        return ResponseEntity.ok().body(CreateAppointmentResponse.builder().build());
    }
}
