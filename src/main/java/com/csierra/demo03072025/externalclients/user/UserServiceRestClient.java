package com.csierra.demo03072025.externalclients.user;

import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.externalclients.user.model.UserCreateRequest;
import com.csierra.demo03072025.externalclients.user.model.UserSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.csierra.demo03072025.persistence.AppointmentState.CREATED;
import static com.csierra.demo03072025.persistence.AppointmentState.NOTIFIED;

@Component
@Slf4j
public class UserServiceRestClient {

    //TODO: POST best verb to use here?
    // Equivalent to POST userServiceBaseUrl/user/search
    public ResponseEntity<User> findUser(UserSearchRequest searchRequest) {
        if (Set.of(CREATED.toString(), NOTIFIED.toString()).contains(searchRequest.getName().toUpperCase())) {
            User matchingUser;
            if (CREATED.toString().equalsIgnoreCase(searchRequest.getName())) {
                matchingUser = buildExistingUser(CREATED.toString());
            } else {
                matchingUser = buildExistingUser(NOTIFIED.toString());
            }
            log.info("Found existing user for request, userId: {}", matchingUser.getId());
            return ResponseEntity.ok(matchingUser);
        }
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
    }

    private User buildExistingUser(String appointmentState) {
        return User.builder()
                .id(appointmentState)
                .name("Carlos Sierra")
                .dateOfBirth(OffsetDateTime.now()) //public repo, so minimizing personal information
                .governmentId("someGovernmentIdNumber")
                .build();
    }

    // Equivalent to PUT userServiceBaseUrl/user/create
    public ResponseEntity<User> createUser(UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(buildUserFromProvided(userCreateRequest));
    }

    private User buildUserFromProvided(UserCreateRequest userCreateRequest) {
        return User.builder()
                .id("1")
                .name(userCreateRequest.getName())
                .governmentId(userCreateRequest.getGovernmentId())
                .emailAddress(userCreateRequest.getEmailAddress())
                .build();
    }
}
