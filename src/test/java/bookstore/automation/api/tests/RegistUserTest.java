package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseTest;
import bookstore.automation.api.domain.RegistUserDmn;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import static bookstore.automation.api.payloads.RegistUserPld.registUser;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class RegistUserTest extends BaseTest {
    private final String userName = faker.address().firstName();
    private final String password = getSecret("PASSWORD");

    @Test
    @Tag("rgtNewAccount")
    @DisplayName("Registration of a new account return - 201")
    public void newAccountRegistration() {
        RegistUserDmn UserForRegistration = new RegistUserDmn(userName, password);
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_CREATED).
                body(
                        "userID", is(notNullValue()),
                        "username", is(notNullValue()),
                        "books", is(notNullValue()),
                        "username", is(userName),
                        "books", empty());
    }

    @Test
    @Tag("rgtBlankUserName")
    @DisplayName("Create an account with blank userName return - 400")
    public void createAnAccountWithBlankUserName() {
        RegistUserDmn UserForRegistration = new RegistUserDmn("", password);
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtBlankPassword")
    @DisplayName("Create an account with blank password return - 400")
    public void createAnAccountWithBlankPassword() {
        RegistUserDmn UserForRegistration = new RegistUserDmn(userName, "");
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtBlankUserNamePasswd")
    @DisplayName("Create an account with blank username and password return - 400")
    public void createAnAccountWithBlankUsernameAndPassword() {
        RegistUserDmn UserForRegistration = new RegistUserDmn("", "");
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(
                        "code", is("1200"),
                        "message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtNotCharacters")
    @DisplayName("Create an account with a password that does not contain special characters return - 400")
    public void createAnAccountWithaPasswordThatDoesNotContainSpecialCharacters() {
        RegistUserDmn UserForRegistration = new RegistUserDmn(userName, "passwd");
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body(
                        "code", is("1300"),
                        "message", is(
                                "Passwords must have at least one non alphanumeric character, " +
                                "one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), " +
                                "one lowercase (\'a\'-\'z\'), one special character and " +
                                "Password must be eight characters or longer."));
    }

    @Test
    @Tag("rgtExistingAccount")
    @DisplayName("Create an account with the same data as an existing account return - 400")
    public void createAnAccountWithTheSameDataAsAnExistingAccount() {
        RegistUserDmn UserForRegistration = new RegistUserDmn("user1", password);
        registUser(UserForRegistration).then().
                statusCode(HttpStatus.SC_NOT_ACCEPTABLE).
                body(
                        "code", is("1204"),
                        "message", is("User exists!"));
    }

}
