package com.csierra.demo03072025.persistence;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
//TODO: this should be an entity corresponding to a DB table
public class Appointment {
    String id;
    String userId;
    String propertyId;
    OffsetDateTime appointmentTime;
    String officeId;
    String agentId;
    AppointmentState appointmentState;
}
