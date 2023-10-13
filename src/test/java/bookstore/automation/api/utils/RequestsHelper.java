package bookstore.automation.api.utils;

import bookstore.automation.api.domain.LoginDmn;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;

public class RequestsHelper {

    public static String getTokenLoginUer(String userName, String Password) {
        LoginDmn user = new LoginDmn(userName, Password);
        return RestAssured
                .given()
                .body(user)
                .when()
                .post("/GenerateToken")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().path("token").toString();
    }

    public static String getUserId(String userName, String Password) {
        LoginDmn user = new LoginDmn(userName, Password);
        return RestAssured
                .given()
                .body(user)
                .when()
                .post("/User")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().path("userID").toString();
    }

}