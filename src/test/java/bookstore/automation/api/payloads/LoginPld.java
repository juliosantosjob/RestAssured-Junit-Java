package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginPld {

    public static Response login(Object loginUsr, String s) {
        return RestAssured
                .given()
                .body(loginUsr)
                .when()
                .post("/GenerateToken");
    }

}
