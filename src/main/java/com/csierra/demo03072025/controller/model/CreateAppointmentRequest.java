package com.csierra.demo03072025.controller.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class CreateAppointmentRequest {
    @NonNull
    AppointmentUser user;
    @NonNull
    String propertyId;
    @NonNull
    OffsetDateTime appointmentTime;
    @NonNull
    String officeId;
    @NonNull
    String agentId;
}
