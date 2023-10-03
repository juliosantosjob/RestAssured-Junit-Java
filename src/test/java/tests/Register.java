package tests;


import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.javafaker.Faker;

import support.BaseApi;

import io.restassured.RestAssured;
import jdk.jfr.Description;

import static org.hamcrest.Matchers.*;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * @author Julio C. Santos
 */

public class Register extends BaseApi {
    private final JSONObject requestBody = new JSONObject();
    private final Faker faker = new Faker();
    private String userName = faker.address().firstName();;

    @Test
    @Description("New account registration return 201")
    public void newAccountRegistration() {
        requestBody.put("userName", userName);
        requestBody.put("password", "Mudar@123");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
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
                        "books", empty()
                    );
    }

    @Test
    @Description("Create an account with blank userName return 400")
    public void createAnAccountWithBlankUserName() {
        requestBody.put("userName", "");
        requestBody.put("password", "Mudar@123");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
                .when()
                    .post("/Account/v1/User")
                .then()
                    .statusCode(400)
                    .body(
                        containsString("code"),
                        containsString("message"))
                    .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required.")
                    );
    }

    @Test
    @Description("Create an account with blank password return 400")
    public void createAnAccountWithBlankPassword() {
        requestBody.put("userName", userName);
        requestBody.put("password", "");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
                .when()
                    .post("/Account/v1/User")
                .then()
                    .statusCode(400)
                    .body(
                        containsString("code"),
                        containsString("message"))
                    .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required.")
                    );
    }

    @Test
    @Description("Create an account with blank username and password")
    public void createAnAccountWithBlankUsernameAndPassword() {
        requestBody.put("userName", "");
        requestBody.put("password", "");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
                .when()
                    .post("/Account/v1/User")
                .then()
                    .statusCode(400)
                    .body(
                        containsString("code"),
                        containsString("message"))
                    .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required.")
                    );
    }

    @Test
    @Description("Create an account with a password that does not contain special characters return 400")
    public void createAccountWithAPasswordThatDoesNotContainSpecialCharacters() {
        requestBody.put("userName", userName);
        requestBody.put("password", "passwd");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
                .when()
                    .post("/Account/v1/User")
                .then()
                    .statusCode(400)
                    .body(
                        containsString("code"),
                        containsString("message"))
                    .body(
                        "code", is("1300"),
                        "message", is(
                                    "Passwords must have at least one non alphanumeric character, " +
                                    "one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), " +
                                    "one lowercase (\'a\'-\'z\'), one special character and " +
                                    "Password must be eight characters or longer."
                                )
                    );
    }

    @Test
    @Description("Create an account with the same data as an existing account return 400")
    public void createAnAccountWithTheSameDataAsAnExistingAccount() {
        requestBody.put("userName", "user1");
        requestBody.put("password", "Mudar@123");

        RestAssured
                .given()
                    .body(requestBody.toJSONString())
                .when()
                    .post("/Account/v1/User")
                .then()
                    .statusCode(406)
                    .body(
                            containsString("code"),
                            containsString("message"))
                    .body(
                            "code", is("1204"),
                            "message", is("User exists!")
                    );
    }

}