package com.csierra.demo03072025.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
//TODO: this should correspond to a DB table, use persistence
public class Appointment {
    String id;
    String userId;
    String propertyId;
    LocalDateTime appointmentTime;
    String officeId;
    String agentId;
    AppointmentStatus appointmentStatus;
}
