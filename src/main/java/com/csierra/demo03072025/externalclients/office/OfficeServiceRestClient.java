package com.csierra.demo03072025.externalclients.office;

import com.csierra.demo03072025.externalclients.office.model.Office;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OfficeServiceRestClient {

    //Equivalent to GET officeServiceBaseUrl/rest/office/{officeId}
    public ResponseEntity<Office> getOffice(String officeId) {
        if (officeId.equals("1")) {
            return ResponseEntity.ok(Office.builder().build());
        } else {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
    }
}
