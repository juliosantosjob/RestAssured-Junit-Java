package bookstore.automation.api.tests;

import bookstore.automation.api.domain.LoginDmn;
import bookstore.automation.api.support.BaseTest;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static bookstore.automation.api.payloads.LoginPld.loginUser;
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
        LoginDmn user = new LoginDmn(userName, password);
        loginUser(user)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("token", is(notNullValue()))
                    .body("expires", is(notNullValue()))
                    .body("status", is("Success"))
                    .body("result", is("User authorized successfully."));
    }

    /* 
     * In the next scenarios there is no status code validation as the correct
     * would return 'Bad Request' but the api is returning 'OK'.
     */
    
    @Test
    @Tag("invalidUserName")
    @DisplayName("Login with invalid username return - 400")
    public void loginWithInvalidUsername() {
        LoginDmn user = new LoginDmn("invalid_userName", password);
        loginUser(user)
                .then()
                    .body("status", is("Failed"))
                    .body("result", is("User authorization failed."));
    }

    @Test
    @Tag("invalidPassword")
    @DisplayName("Login with invalid password return - 400")
    public void loginWithInvalidPassword() {
        LoginDmn user = new LoginDmn(userName, "invalid_password");
        loginUser(user)
                .then()
                    .body("status", is("Failed"))
                    .body("result", is("User authorization failed."));
    }

    @Test
    @Tag("invalidUsrPasswd")
    @DisplayName("Login with invalid username and password  return - 400")
    public void loginWithInvalidUsernameAndPassword() {
        LoginDmn user = new LoginDmn("invalid_userName", "invalid_password");
        loginUser(user)
                .then()
                    .body("status", is("Failed"))
                    .body("result", is("User authorization failed."));
    }

}
