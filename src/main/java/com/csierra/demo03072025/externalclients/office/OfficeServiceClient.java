package com.csierra.demo03072025.externalclients.office;

import com.csierra.demo03072025.externalclients.office.model.Office;
import org.springframework.stereotype.Component;

@Component
public class OfficeServiceClient {

    public Office getOffice(String officeId) {
        if (officeId.equals("1")) {
            return Office.builder().build();
        } else {
            return null;
        }
    }
}
