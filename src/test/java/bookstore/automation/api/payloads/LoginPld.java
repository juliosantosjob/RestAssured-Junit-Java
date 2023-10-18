package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginPld {

    public static Response loginUser(Object user) {
        return RestAssured
                .given()
                .body(user)
                .post("/Account/v1/GenerateToken");
    }

}