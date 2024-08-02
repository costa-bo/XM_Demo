package demo.utilsCommon.ws;

import demo.utilsCommon.ws.rest.Rest;
import net.serenitybdd.rest.SerenityRest;

public class WebService {

    public static Rest rest = new Rest();

    /**
     * Method to get the response from the latest request
     * @return the latest response as String
     */
    protected String getLastResponse() {
        return SerenityRest.lastResponse().body().asString();
    }
}
