package com.csierra.demo03072025.externalclients.notification.model;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class NotificationRequest {
    //TODO: should we allow for more general delivery mechanisms?
    String recipientEmail;
    String messageTemplateName;
    //Additional information used to populate the specified message template, Appointment in our case
    Map<String, Object> metadata;
}
