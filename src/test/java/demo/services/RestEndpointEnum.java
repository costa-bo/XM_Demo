package demo.services;

import demo.utils.config.AppConfig;
import io.restassured.RestAssured;

public enum RestEndpointEnum {
    GET_FILM("/films"),
    GET_PEOPLE("/people");

    RestEndpointEnum(String path) { this.path = path; }

    private String path;

    public String getURL() {
        RestAssured.baseURI = AppConfig.restBaseURL;
        return AppConfig.restBaseURL + path;
    }
}
