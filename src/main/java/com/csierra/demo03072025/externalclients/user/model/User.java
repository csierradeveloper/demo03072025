package com.csierra.demo03072025.externalclients.user.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.OffsetDateTime;

@Value
@Builder
public class User {
    @NonNull
    String id;
    String name;
    OffsetDateTime dateOfBirth;
    String governmentId;
}
