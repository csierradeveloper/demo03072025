package com.csierra.demo03072025.service;

import com.csierra.demo03072025.controller.model.AppointmentUser;
import com.csierra.demo03072025.externalclients.user.UserServiceRestClient;
import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.externalclients.user.model.UserCreateRequest;
import com.csierra.demo03072025.externalclients.user.model.UserSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserService {

    @Autowired
    UserServiceRestClient userClient;

    public User getUser(AppointmentUser appointmentUser) {

        UserSearchRequest searchRequest = UserSearchRequest.builder()
                .name(appointmentUser.getName())
                .governmentId(appointmentUser.getGovernmentId())
                // other search fields here
                .build();
        ResponseEntity<User> userSearchResult = userClient.findUser(searchRequest);

        if (userSearchResult.getStatusCode().is2xxSuccessful()) {
            return userSearchResult.getBody();
        }

        UserCreateRequest createRequest = UserCreateRequest.builder()
                .name(appointmentUser.getName())
                .emailAddress(appointmentUser.getEmailAddress())
                .governmentId(appointmentUser.getGovernmentId())
                // other provided fields about the user here
                .build();
        return userClient.createUser(createRequest).getBody();
    }
}
