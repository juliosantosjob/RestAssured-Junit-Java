package bookstore.automation.api.tests;

import bookstore.automation.api.domain.LoginDmn;
import bookstore.automation.api.support.BaseTest;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static bookstore.automation.api.payloads.LoginPld.login;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class LoginTest extends BaseTest {
    private final String userName = getSecret("NAME");
    private final String password = getSecret("PASSWORD");

    @Test
    @Tag("loginSuccess")
    @DisplayName("Login sucessfully return - 200")
    public void loginSucessfully() {
<<<<<<< HEAD
        LoginDmn user = new LoginDmn(userName, password);
        login(user).
                then().
=======
        Login user = new Login(userName, password);
        login(user).then().
>>>>>>> 8ebb135a3d641b1a652fc9d0ff543b1e19822dda
                statusCode(HttpStatus.SC_OK).
                body(
                        "token", is(notNullValue()),
                        "expires", is(notNullValue()),
                        "status", is("Success"),
                        "result", is("User authorized successfully."));
    }

    @Test
    @Tag("invalidUserName")
    @DisplayName("Login with invalid username return - 400")
    public void loginWithInvalidUsername() {
        LoginDmn user = new LoginDmn("invalid_userName", password);
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @Tag("invalidPassword")
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        LoginDmn user = new LoginDmn(userName, "invalid_password");
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

    @Test
    @Tag("invalidLoginPasswd")
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        LoginDmn user = new LoginDmn("invalid_userName", "invalid_password");
        login(user).then().
                body(
                        "status", is("Failed"),
                        "result", is("User authorization failed."));
    }

}
