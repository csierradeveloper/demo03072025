package com.csierra.demo03072025.appointment.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class SearchAppointmentRequest {
    String userId;
    String propertyId;
    OffsetDateTime appointmentTime;
    String officeId;
    String agentId;
}
