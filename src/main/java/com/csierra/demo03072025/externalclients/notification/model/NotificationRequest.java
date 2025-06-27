package com.csierra.demo03072025.externalclients.notification.model;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class NotificationRequest {
    //Note: we might want to allow for more general delivery mechanisms, keeping this simple as that is outside the scope of this project
    String recipientEmail;
    String messageTemplateName;
    //Additional information used to populate the specified message template, Appointment in our case
    Map<String, Object> metadata;
}
