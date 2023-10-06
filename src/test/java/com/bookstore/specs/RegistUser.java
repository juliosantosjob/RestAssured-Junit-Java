package com.bookstore.specs;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.javafaker.Faker;

import com.bookstore.support.BaseApi;

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
public class RegistUser extends BaseApi {
    public static final Map<String, Object> payload = new HashMap<>();
    public static JSONObject payloadJson;
    private static final Faker faker = new Faker();
    private static final String userName = faker.address().firstName();

    @Test
    @DisplayName("Registration of a new account return 201")
    public void newAccountRegistration() {
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
    @DisplayName("Create an account with blank userName return 400")
    public void createAnAccountWithBlankUserName() {
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
    @DisplayName("Create an account with blank password return 400")
    public void createAnAccountWithBlankPassword() {
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
    @DisplayName("Create an account with blank username and password")
    public void createAnAccountWithBlankUsernameAndPassword() {
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
    @DisplayName("Create an account with a password that does not contain special characters return 400")
    public void createAnAccountWithaPasswordThatDoesNotContainSpecialCharacters() {
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
    @DisplayName("Create an account with the same data as an existing account return 400")
    public void createAnAccountWithTheSameDataAsAnExistingAccount() {
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