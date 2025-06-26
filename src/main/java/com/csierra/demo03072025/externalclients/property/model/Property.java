package com.csierra.demo03072025.externalclients.property.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Map;

@Value
@Builder
public class Property {
    String id;
    BigDecimal assessedValue;
    Map<String, Object> metadata;
}
