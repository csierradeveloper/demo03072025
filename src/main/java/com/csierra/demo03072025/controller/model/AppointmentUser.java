package com.csierra.demo03072025.controller.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class AppointmentUser {
    String name;
    OffsetDateTime dateOfBirth;
    String governmentId;
    //other fields used to find the user or create if not already existing
}
