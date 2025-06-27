package com.csierra.demo03072025.controller.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class AppointmentUser {
    @NonNull
    String name;
    OffsetDateTime dateOfBirth;
    String governmentId;
    @NonNull
    String emailAddress;
    //other fields used to find the user or create if not already existing
}
