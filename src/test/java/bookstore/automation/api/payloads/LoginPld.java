package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginPld {

    public static Response login(Object user) {
        return RestAssured
                .given()
                .body(user)
                .when()
                .post("/GenerateToken");
    }

}
