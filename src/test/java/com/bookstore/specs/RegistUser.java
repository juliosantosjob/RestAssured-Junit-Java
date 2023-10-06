package com.bookstore.specs;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.javafaker.Faker;

import com.bookstore.support.BaseApi;

import io.restassured.RestAssured;
import jdk.jfr.Description;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Julio C. Santos
 */

public class RegistUser extends BaseApi {
        public static final Map<String, String> payload = new HashMap<>();
        public static JSONObject payloadJson;
        private static final Faker faker = new Faker();
        private static final String userName = faker.address().firstName();

        @Test
        @Description("New account registration return - 201")
        public void new_account_registration() {
                payload.put("userName", userName);
                payload.put("password", "Mudar@123");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_CREATED)
                        .body(
                                "userID", is(notNullValue()),
                                "username", is(notNullValue()),
                                "books", is(notNullValue()),
                                "username", is(userName),
                                "books", empty());
        }

        @Test
        @Description("Create an account with blank userName return 400")
        public void create_an_account_with_blank_userName() {
                payload.put("userName", "");
                payload.put("password", "Mudar@123");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(
                                "code", is("1200"),
                                "message", is("UserName and Password required."));
        }

        @Test
        @Description("Create an account with blank password return 400")
        public void create_an_account_with_blank_password() {
                payload.put("userName", userName);
                payload.put("password", "");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(
                                "code", is("1200"),
                                "message", is("UserName and Password required."));
        }

        @Test
        @Description("Create an account with blank username and password")
        public void create_an_account_with_blank_username_and_password() {
                payload.put("userName", "");
                payload.put("password", "");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(
                                "code", is("1200"),
                                "message", is("UserName and Password required."));
        }

        @Test
        @Description("Create an account with a password that does not contain special characters return 400")
        public void create_an_account_with_a_password_that_does_not_contain_special_characters() {
                payload.put("userName", userName);
                payload.put("password", "passwd");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .body(
                                "code", is("1300"),
                                "message", is(
                                        "Passwords must have at least one non alphanumeric character, " +
                                        "one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), " +
                                        "one lowercase (\'a\'-\'z\'), one special character and " +
                                        "Password must be eight characters or longer."));
        }

        @Test
        @Description("Create an account with the same data as an existing account return 400")
        public void create_an_account_with_the_same_data_as_an_existing_account() {
                payload.put("userName", "user1");
                payload.put("password", "Mudar@123");
                payloadJson = new JSONObject(payload);

                RestAssured
                        .given()
                                .body(payloadJson.toJSONString())
                        .when()
                                .post("/Account/v1/User")
                        .then()
                                .statusCode(HttpStatus.SC_NOT_ACCEPTABLE)
                        .body(
                                "code", is("1204"),
                                "message", is("User exists!"));
        }

}