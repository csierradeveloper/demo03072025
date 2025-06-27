package com.csierra.demo03072025.controller.model;

import com.csierra.demo03072025.persistence.AppointmentState;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateAppointmentResponse {
    String appointmentId;
    AppointmentState appointmentStatus;
}
