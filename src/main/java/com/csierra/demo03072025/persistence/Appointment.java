package com.csierra.demo03072025.persistence;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
//This would be an entity corresponding to a DB table, actually creating would be outside the scope of this project
public class Appointment {
    String id;
    String userId;
    String propertyId;
    OffsetDateTime appointmentTime;
    String officeId;
    String agentId;
    AppointmentState appointmentState;
}
