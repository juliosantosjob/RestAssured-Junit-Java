package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RegisterUserPld {

    public static Response registerUser(Object objectUserRegister) {
        return RestAssured
                .given()
                .body(objectUserRegister)
                .when()
                .post("/Account/v1/User");
    }

}