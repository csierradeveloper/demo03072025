package com.csierra.demo03072025.externalclients.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSearchRequest {
    String name;
    String governmentId;

    // insert other fields used to search for an existing user
}
