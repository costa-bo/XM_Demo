package demo.utilsCommon.ws.rest;

import demo.utilsCommon.ws.WebService;

public class Rest extends WebService {

    public RestValidations validate;

    public Rest() {
        validate = new RestValidations(this);
    }

    /**
     * Method to get the response from the latest REST request
     * @return the latest REST response as String
     */
    public String getLastResponse() {
        return super.getLastResponse();
    }

}
