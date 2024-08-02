package demo.utilsCommon.ws;

import net.serenitybdd.rest.SerenityRest;

public class WebServiceValidation {

    /**
     * Method to verify that the returned Status Code is equal to an expected.
     * @param expectedStatusCode
     */
    protected void verifyStatusCode(int expectedStatusCode) {
        SerenityRest
                .restAssuredThat(response -> response.statusCode(expectedStatusCode));
    }

    /**
     * Method to verify that the returned Text contains an expected content.
     * @param expectedText
     */
    protected void verifyResponseText(String expectedText) {
        SerenityRest
                .restAssuredThat(response -> response.toString().contains(expectedText));
    }

    protected boolean verifyStatusCode2(int expectedStatusCode) {
        try {
            System.out.println("costa4");
            SerenityRest
                    .restAssuredThat(response -> response.statusCode(expectedStatusCode));
        } catch (Exception e){
            System.out.println("costa1");
            return false;
        }
        System.out.println("costa2");
        return true;
    }
}
