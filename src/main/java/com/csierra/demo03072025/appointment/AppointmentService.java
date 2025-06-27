package com.csierra.demo03072025.appointment;

import com.csierra.demo03072025.appointment.model.Appointment;
import com.csierra.demo03072025.appointment.model.AppointmentState;
import com.csierra.demo03072025.appointment.model.SearchAppointmentRequest;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Set;

import static com.csierra.demo03072025.appointment.model.AppointmentState.CREATED;
import static com.csierra.demo03072025.appointment.model.AppointmentState.NOTIFIED;

@Service
public class AppointmentService {

    //TODO: have an appointment repo here, SQL-based

    /**
     * This should take a search request, use it to construct a query to be run against a database managed by this
     * microservice, and then return an Optional, or return an Appointment that could be null. Since we are mocking
     * external dependencies, instead I'm using userId to determine whether we want to show the case where
     * an appointment already exists, or create a new one
     */
    public Appointment findExistingAppointment(SearchAppointmentRequest request) {
        //In actuality, this should search a repository (corresponding to a DB managed by this service) for existing appointments
        if (Set.of(CREATED.toString(), NOTIFIED.toString()).contains(request.getUserId())) {
            return buildAppointment(request, AppointmentState.valueOf(request.getUserId()));
        }

        return null;
    }

    private Appointment buildAppointment(SearchAppointmentRequest request, AppointmentState expectedState) {
        return Appointment.builder()
                .id("")
                .userId(request.getUserId())
                .propertyId(request.getPropertyId())
                .appointmentTime(OffsetDateTime.now().plusWeeks(10))
                .officeId(request.getOfficeId())
                .agentId(request.getAgentId())
                .appointmentState(expectedState)
                .build();
    }

    public Appointment createAppointment(SearchAppointmentRequest request) {
        return buildAppointment(request, CREATED);
    }
}
