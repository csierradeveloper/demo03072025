package com.csierra.demo03072025.externalclients.user;

import com.csierra.demo03072025.controller.model.AppointmentUser;
import com.csierra.demo03072025.externalclients.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.csierra.demo03072025.appointment.model.AppointmentState.CREATED;
import static com.csierra.demo03072025.appointment.model.AppointmentState.NOTIFIED;

@Slf4j
@Component
public class UserRestClient {

    /**
     * In an actual implementation we'd map from AppointmentUser to a UserSearchRequest object defined by the UserClient,
     * and use that to perform a search by submitting a request to a REST client provided by the microservice being queried.
     * As we are mocking surrounding systems, and to minimize busywork, we instead pass in the request object directly
     * and just determine behavior depending on which name is provided. Names corresponding to appointmentState are
     * used to allow demonstration of behavior for appointment-created, appointment-notified states.
     * <p>
     * Actual implementation would likely use a mapper library to go from local request object to client request object,
     * would expect a Response or response object from the client, and use that to either return a User or null, or
     * {@code Optional<User>}
     */
    public User findMatchingUser(AppointmentUser appointmentUser) {
        log.error(Set.of(CREATED.toString(), NOTIFIED.toString()).toString());

        if (Set.of(CREATED.toString(), NOTIFIED.toString()).contains(appointmentUser.getName().toUpperCase())) {
            User matchingUser;
            if (CREATED.toString().equalsIgnoreCase(appointmentUser.getName())) {
                matchingUser = buildExistingUser(CREATED.toString());
            } else {
                matchingUser = buildExistingUser(NOTIFIED.toString());
            }
            log.info("Found existing user for request, userId: {}", matchingUser.getId());
            return matchingUser;
        }
        return null;
    }

    private User buildExistingUser(String appointmentState) {
        return User.builder()
                .id(appointmentState)
                .name("Carlos Sierra")
                .dateOfBirth(OffsetDateTime.now()) //public repo, so minimizing personal information
                .governmentId("someGovernmentIdNumber")
                .build();
    }

    /**
     * This would be a PUT in the client, recording the provided user information in their persistence and
     * getting a User object with a valid user ID. As above, eliding several steps for simplicity.
     */
    public User createUser(AppointmentUser appointmentUser) {
        return buildUserFromProvided(appointmentUser);
    }

    //TODO: replace with mapper?
    private User buildUserFromProvided(AppointmentUser appointmentUser) {
        return User.builder()
                .id("1")
                .name(appointmentUser.getName())
                .governmentId(appointmentUser.getGovernmentId())
                .dateOfBirth(appointmentUser.getDateOfBirth())
                .build();
    }
}
