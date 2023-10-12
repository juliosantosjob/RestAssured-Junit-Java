package bookstore.automation.api.tests;

import bookstore.automation.api.domain.Login;
import bookstore.automation.api.support.BaseTest;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static bookstore.automation.api.payloads.LoginPld.login;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

public class LoginTest extends BaseTest {
    private static final String userName = getSecret("NAME");
    private static final String password = getSecret("PASSWORD");

    @Test
    @DisplayName("Login sucessfully return - 200")
    public void loginSucessfully() {
        Login user = new Login(userName, password);
        login(user).then().
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
        Login user = new Login("invalid_userName", password);
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        Login user = new Login(userName, "invalid_password");
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        Login user = new Login("invalid_userName", "invalid_password");
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

}
