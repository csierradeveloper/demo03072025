package com.csierra.demo03072025.persistence;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class AppointmentRequest {
    String userId;
    String propertyId;
    OffsetDateTime appointmentTime;
    String officeId;
    String agentId;
}
