package bookstore.automation.api.tests;

import bookstore.automation.api.domain.Login;
import bookstore.automation.api.support.BaseTest;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static bookstore.automation.api.payloads.LoginPld.login;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Login sucessfully return - 200")
    public void loginSucessfully() {
        Login user = new Login("QA_2", "Test@123");
        login(user, "Test@123").
                then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "token", is(notNullValue()),
                        "expires", is(notNullValue()),
                        "status", is("Success"),
                        "result", is("User authorized successfully."));
    }

    @Test
    @DisplayName("Login with invalid username return - 400")
    public void loginWithInvalidUsername() {
        Login user = new Login("invalid_userName", "Test@123");
        login(user, "Test@123").then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        Login user = new Login("QA_2", "invalid_password");
        login(user, "Test@123").then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        Login user = new Login("invalid_userName", "invalid_password");
        login(user, "Test@123").then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

}
