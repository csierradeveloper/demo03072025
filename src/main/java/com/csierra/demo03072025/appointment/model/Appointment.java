package com.csierra.demo03072025.appointment.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
//TODO: this should correspond to a DB table, use persistence
public class Appointment {
    String id;
    String userId;
    String propertyId;
    OffsetDateTime appointmentTime;
    String officeId;
    String agentId;
    AppointmentState appointmentState;
}
