package com.csierra.demo03072025.service;

import com.csierra.demo03072025.externalclients.notification.NotificationServiceRestClient;
import com.csierra.demo03072025.externalclients.notification.model.NotificationRequest;
import com.csierra.demo03072025.persistence.Appointment;
import com.csierra.demo03072025.externalclients.notification.model.NotificationResponse;
import com.csierra.demo03072025.externalclients.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationService {

    @Autowired
    NotificationServiceRestClient notificationServiceRestClient;

    /**
     * We are assuming that a RESTful Notifications system exists which takes a recipient, a message template (identified
     * by a String here, in reality it'd be a list of Enums provided by the NotificationServiceRestClient), and template-specific
     * metadata used to populate the message. We return information about whether the notification system accepted the message.
     */
    public boolean sendAppointmentNotification(User user, Appointment appointment) {
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .recipientEmail(user.getEmailAddress())
                .messageTemplateName("appointmentNotification")
                .metadata(Map.of("appointment", appointment))
                .build();

        ResponseEntity<NotificationResponse> notificationDeliveryResult = notificationServiceRestClient.deliverNotification(notificationRequest);

        return notificationDeliveryResult.getStatusCode().is2xxSuccessful() && notificationDeliveryResult.getBody().getMessageAcceptedForDelivery();
    }
}
