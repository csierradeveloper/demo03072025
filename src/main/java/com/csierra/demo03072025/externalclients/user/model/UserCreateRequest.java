package com.csierra.demo03072025.externalclients.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateRequest {
    String name;
    String governmentId;
    String emailAddress;

    // other fields used to create a user here
}
