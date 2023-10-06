package com.bookstore.specs;

import com.bookstore.support.BaseApi;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class Login extends BaseApi {
    public static final Map<String, Object> payload = new HashMap<>();
    public static JSONObject payloadJson;

    @Test
    @Tag("login")
    @DisplayName("Login sucessfully return - 200")
    public void loginSucessfully() {
        payload.put("userName", "QA_2");
        payload.put("password", "Test@123");
        payloadJson = new JSONObject(payload);

        RestAssured
                .given()
                .body(payloadJson.toJSONString())
                .when()
                .post("/Account/v1/GenerateToken")
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
        payload.put("userName", "invalid_userName");
        payload.put("password", "Test@123");
        payloadJson = new JSONObject(payload);

        RestAssured
                .given()
                .body(payloadJson.toJSONString())
                .when()
                .post("/Account/v1/GenerateToken")
                .then().body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        payload.put("userName", "QA_2");
        payload.put("password", "invalid_password");
        payloadJson = new JSONObject(payload);

        RestAssured
                .given()
                .body(payloadJson.toJSONString())
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        payload.put("userName", "invalid_userName");
        payload.put("password", "invalid_password");
        payloadJson = new JSONObject(payload);

        RestAssured
                .given()
                .body(payloadJson.toJSONString())
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }
}