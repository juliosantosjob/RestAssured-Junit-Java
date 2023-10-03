package tests;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.javafaker.Faker;

import support.BaseApi;

import io.restassured.RestAssured;
import jdk.jfr.Description;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * @author Julio C. Santos
 */

public class Register extends BaseApi {
    private HashMap<String, Object> requestBody = new HashMap<String, Object>();
    private JSONObject requestBodyJson;
    private Faker faker = new Faker();
    private String userName = faker.address().firstName();;

    @Test
    @Description("New account registration return 201")
    public void new_account_registration() {
        requestBody.put("userName", userName);
        requestBody.put("password", "Mudar@123");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(201)
                .body(
                        containsString("userID"),
                        containsString("username"),
                        containsString("books"))
                .body(
                        "username", is(userName),
                        "books", empty());
    }

    @Test
    @Description("Create an account with blank userName return 400")
    public void create_an_account_with_blank_userName() {
        requestBody.put("userName", "");
        requestBody.put("password", "Mudar@123");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(400)
                .body(
                        containsString("code"),
                        containsString("message"))
                .body(
                        "code", equalTo("1200"),
                        "message", equalTo("UserName and Password required."));
    }

    @Test
    @Description("Create an account with blank password return 400")
    public void create_an_account_with_blank_password() {
        requestBody.put("userName", userName);
        requestBody.put("password", "");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(400)
                .body(
                        containsString("code"),
                        containsString("message"))
                .body(
                        "code", equalTo("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @Description("Create an account with blank username and password")
    public void create_an_account_with_blank_username_and_password() {
        requestBody.put("userName", "");
        requestBody.put("password", "");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(400)
                .body(
                        containsString("code"),
                        containsString("message"))
                .body(
                        "code", equalTo("1200"),
                        "message", equalTo("UserName and Password required."));
    }

    @Test
    @Description("Create an account with a password that does not contain special characters return 400")
    public void create_an_account_with_a_password_that_does_not_contain_special_characters() {
        requestBody.put("userName", userName);
        requestBody.put("password", "passwd");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(400)
                .body(
                        containsString("code"),
                        containsString("message"))
                .body(
                        "code", equalTo("1300"),
                        "message", equalTo(
                                "Passwords must have at least one non alphanumeric character, " +
                                        "one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), " +
                                        "one lowercase (\'a\'-\'z\'), one special character and " +
                                        "Password must be eight characters or longer."));
    }

    @Test
    @Description("Create an account with the same data as an existing account return 400")
    public void create_an_account_with_the_same_data_as_an_existing_account() {
        requestBody.put("userName", "user1");
        requestBody.put("password", "Mudar@123");
        requestBodyJson = new JSONObject(requestBody);

        RestAssured
                .given()
                .body(requestBodyJson.toJSONString())
                .when()
                .post("/Account/v1/User")
                .then()
                .statusCode(406)
                .body(
                        containsString("code"),
                        containsString("message"))
                .body(
                        "code", equalTo("1204"),
                        "message", equalTo("User exists!"));
    }

}