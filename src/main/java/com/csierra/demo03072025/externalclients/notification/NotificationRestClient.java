package com.csierra.demo03072025.externalclients.notification;

import com.csierra.demo03072025.persistence.Appointment;
import com.csierra.demo03072025.externalclients.notification.model.NotificationResponse;
import com.csierra.demo03072025.externalclients.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class NotificationRestClient {

    public NotificationResponse sendAppointmentNotification(User user, Appointment appointment) {
        //try to send a notification

        //handle failure
        return NotificationResponse.builder()
                .messageAcceptedForDelivery(true)
                .build();
    }
}
