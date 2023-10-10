package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseApi;
import bookstore.automation.api.domain.RegistUser;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

public class RegistUserTest extends BaseApi {
    private final Faker faker = new Faker();
    private final String userName = faker.address().firstName();

    @Test
    @DisplayName("Registration of a new account return 201")
    public void newAccountRegistration() {
        RegistUser UserRegistration = new RegistUser(userName, "Mudar@123");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
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
        RegistUser UserRegistration = new RegistUser("", "Mudar@123");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @DisplayName("Create an account with blank password return 400")
    public void createAnAccountWithBlankPassword() {
        RegistUser UserRegistration = new RegistUser(userName, "");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @DisplayName("Create an account with blank username and password")
    public void createAnAccountWithBlankUsernameAndPassword() {
        RegistUser UserRegistration = new RegistUser("", "");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @DisplayName("Create an account with a password that does not contain special characters return 400")
    public void createAnAccountWithaPasswordThatDoesNotContainSpecialCharacters() {
        RegistUser UserRegistration = new RegistUser(userName, "passwd");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
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
        RegistUser UserRegistration = new RegistUser("user1", "Mudar@123");
        RestAssured
                .given()
                .body(UserRegistration)
                .when()
                .post("/User")
                .then()
                .statusCode(HttpStatus.SC_NOT_ACCEPTABLE)
                .body(
                        "code", is("1204"),
                        "message", is("User exists!"));
    }

}