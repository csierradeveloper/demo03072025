package com.csierra.demo03072025.externalclients.notification.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NotificationResponse {
    @NonNull
    Boolean messageAcceptedForDelivery;
}
