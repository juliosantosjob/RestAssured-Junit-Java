package bookstore.automation.api.tests;

import bookstore.automation.api.domain.Login;
import bookstore.automation.api.support.BaseApi;

import org.apache.http.HttpStatus;


import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

public class LoginTest extends BaseApi {

    @Test
    @DisplayName("Login sucessfully return - 200")
    public void loginSucessfully() {
        Login loginUsr = new Login("QA_2", "Test@123");
        RestAssured
                .given()
                .body(loginUsr)
                .when()
                .post("/GenerateToken")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(
                        "token", is(notNullValue()),
                        "expires", is(notNullValue()),
                        "status", is("Success"),
                        "result", is("User authorized successfully."));
    }

    @Test
    @DisplayName("Login with invalid username return - 400")
    public void loginWithInvalidUsername() {
        Login loginUsr = new Login("invalid_userName", "Test@123");
        RestAssured
                .given()
                .body(loginUsr)
                .when()
                .post("/GenerateToken")
                .then().body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        Login loginUsr = new Login("QA_2", "invalid_password");
        RestAssured
                .given()
                .body(loginUsr)
                .when()
                .post("/GenerateToken")
                .then()
                .body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        Login loginUsr = new Login("invalid_userName", "invalid_password");
        RestAssured
                .given()
                .body(loginUsr)
                .when()
                .post("/GenerateToken")
                .then()
                .body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

}