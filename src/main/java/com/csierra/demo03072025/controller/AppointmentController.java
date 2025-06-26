package com.csierra.demo03072025.controller;

import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.controller.model.CreateAppointmentResponse;
import com.csierra.demo03072025.externalclients.user.UserRestClient;
import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.validation.CreateAppointmentRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Service
class AppointmentController {

    @Autowired
    private CreateAppointmentRequestValidator createAppointmentRequestValidator;
    @Autowired
    private UserRestClient userRestClient;

    //TODO: should be a post, or a put?
    @PostMapping("/appointment/create")
    ResponseEntity<CreateAppointmentResponse> createAppointment(@RequestBody CreateAppointmentRequest createAppointmentRequest) {

        createAppointmentRequestValidator.validateRequest(createAppointmentRequest);

        User user = userRestClient.findMatchingUser(createAppointmentRequest.getUser());
        if (user == null) {
            userRestClient.createUser(createAppointmentRequest.getUser());
        }

        //Search for an appointment matching what you're trying to make. If one already exists in anything other than created state, exit early

        //If no appointment matching desired time/place/customer exists, create one in PENDING

        //Send request to notification service, get response back

        //If response is successful, update appointment status

        //return 200 (or appropriate response code)

        return ResponseEntity.ok().body(CreateAppointmentResponse.builder().build());
    }
}
