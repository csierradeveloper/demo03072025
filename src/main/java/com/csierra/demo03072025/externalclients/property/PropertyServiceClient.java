package com.csierra.demo03072025.externalclients.property;

import com.csierra.demo03072025.externalclients.property.model.Property;
import org.springframework.stereotype.Component;

@Component
public class PropertyServiceClient {

    public Property getProperty(String propertyId) {
        if (propertyId.equals("1")) {
            return Property.builder().build();
        } else {
            return null;
        }
    }
}
