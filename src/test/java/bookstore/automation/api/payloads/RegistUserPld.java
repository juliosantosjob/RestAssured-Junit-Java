package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RegistUserPld {

    public static Response registUser(Object UserRegistration) {
        return RestAssured.
                given()
                .body(UserRegistration)
                .when()
                .post("/User");
    }

}
