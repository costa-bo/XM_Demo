package demo.services;


import demo.utilsCommon.ws.WebService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RestValidations {

    private final Rest instance;

    RestValidations(Rest instance) {
        this.instance = instance;
    }

    public Rest verifyStatusCodes(int... statusCodes) {
        List<Integer> statusCodesList = new ArrayList<>();
        for (int statusCode : statusCodes) {
            statusCodesList.add(statusCode);
        }
        WebService.rest.validate.verifyStatusCodes(statusCodesList);
        return instance;
    }

    public void verifyNewCustomer(Properties testData) {
        WebService.rest.validate.verifyResponseText(testData.getProperty("firstName"));
    }

    public void verifyProduct() {
        WebService.rest.validate.verifyResponseText("Sleeveless");
    }
}
