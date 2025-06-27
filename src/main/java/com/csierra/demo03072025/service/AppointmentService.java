package com.csierra.demo03072025.service;

import com.csierra.demo03072025.controller.model.CreateAppointmentRequest;
import com.csierra.demo03072025.externalclients.user.model.User;
import com.csierra.demo03072025.persistence.Appointment;
import com.csierra.demo03072025.persistence.AppointmentRequest;
import com.csierra.demo03072025.persistence.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public Appointment getAppointment(User user, CreateAppointmentRequest createAppointmentRequest) {

        AppointmentRequest appointmentRequest = buildAppointmentRequest(user, createAppointmentRequest);
        Appointment appointment = appointmentRepository.findExistingAppointment(appointmentRequest);
        if (Objects.isNull(appointment)) {
            appointment = appointmentRepository.createAppointment(appointmentRequest);
        }

        return appointment;
    }

    private AppointmentRequest buildAppointmentRequest(User user, CreateAppointmentRequest createAppointmentRequest) {
        return AppointmentRequest.builder()
                .userId(user.getId())
                .propertyId(createAppointmentRequest.getPropertyId())
                .appointmentTime(createAppointmentRequest.getAppointmentTime())
                .officeId(createAppointmentRequest.getOfficeId())
                .agentId(createAppointmentRequest.getAgentId())
                .build();
    }
}
