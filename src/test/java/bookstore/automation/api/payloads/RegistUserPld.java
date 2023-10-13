package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RegistUserPld {

    public static Response registUser(Object objectUserRegist) {
        return RestAssured
                .given()
                .body(objectUserRegist)
                .when()
                .post("/Account/v1/User");
    }

}