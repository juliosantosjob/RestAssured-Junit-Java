package com.bookstore.specs;

import com.bookstore.support.BaseApi;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.junit.jupiter.api.Tag;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class Login extends BaseApi {
        public static final Map<String, String> payload = new HashMap<>();
        public static JSONObject payloadJson;

        @Test
        @Description("Login sucessfully return - 200")
        public void login_sucessfully() {
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
        @Description("Login with invalid username return - 400")
        public void login_with_invalid_username() {
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
        @Description("Login with invalid password return - 400")
        public void login_with_invalid_password() {
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
        @Description("Login with invalid username and password  return - 400")
        public void login_with_invalid_username_and_password () {
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