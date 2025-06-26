package com.csierra.demo03072025.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CreateAppointmentRequest {
    AppointmentUser user;
    String propertyId;
    LocalDateTime appointmentTime;
    String officeId;
    String agentId;
}
