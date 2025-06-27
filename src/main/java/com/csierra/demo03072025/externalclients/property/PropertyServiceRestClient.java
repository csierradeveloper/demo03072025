package com.csierra.demo03072025.externalclients.property;

import com.csierra.demo03072025.externalclients.property.model.Property;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PropertyServiceRestClient {

    //Equivalent to GET propertyServiceUrl/rest/property/{propertyId}
    public ResponseEntity<Property> getProperty(String propertyId) {
        if (propertyId.equals("1")) {
            return ResponseEntity.ok(Property.builder().build());
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
}
