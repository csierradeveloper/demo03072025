package com.csierra.demo03072025.controller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateAppointmentResponse {
    String appointmentId;
    //TODO: bother to convert to an enum?
    String appointmentStatus;

}
