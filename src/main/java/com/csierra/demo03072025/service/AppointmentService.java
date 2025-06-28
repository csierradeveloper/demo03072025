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

    /**
     * Looks for and returns an existing appointment for this user matching what is desired by CreateAppointmentRequest.
     * If no appointment already exists matching these parameters, a new one is created and returned
     */
    public Appointment getAppointment(User user, CreateAppointmentRequest createAppointmentRequest) {
        AppointmentRequest appointmentRequest = buildAppointmentRequest(user, createAppointmentRequest);
        Appointment appointment = appointmentRepository.findExistingAppointment(appointmentRequest);
        if (Objects.isNull(appointment)) {
            appointment = appointmentRepository.createNewAppointment(appointmentRequest);
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

    // This would actually persist the changes made to the provided Appointment. Since we are mocking all persistence, it does nothing
    public Appointment updateAppointment(Appointment appointment) {
        return appointment;
    }
}
