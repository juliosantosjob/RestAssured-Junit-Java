package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseTest;
import bookstore.automation.api.domain.RegisterUserDamn;
import org.apache.http.HttpStatus;

import org.junit.jupiter.api.*;

import static bookstore.automation.api.payloads.RegisterUserPld.registerUser;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
@TestMethodOrder(OrderAnnotation.class)
public class RegisterTest extends BaseTest {
    private final String userName = faker.address().firstName();
    private final String password = getSecret("PASSWORD");

    @Test
    @Tag("rgtNewAccount")
    @DisplayName("Registration of a new account return - 201")
    public void newAccountRegistration() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn(userName, password);
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body("userID", is(notNullValue()))
                    .body("username", is(notNullValue()))
                    .body("books", is(notNullValue()))
                    .body("username", is(userName))
                    .body("books", is(empty()));
    }

    @Test
    @Tag("rgtBlankUserName")
    @DisplayName("Create an account with blank userName return - 400")
    public void createAnAccountWithBlankUserName() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn("", password);
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("code", is("1200"))
                    .body("message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtBlankPassword")
    @DisplayName("Create an account with blank password return - 400")
    public void createAnAccountWithBlankPassword() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn(userName, "");
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("code", is("1200"))
                    .body("message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtBlankUserNamePasswd")
    @DisplayName("Create an account with blank username and password return - 400")
    public void createAnAccountWithBlankUsernameAndPassword() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn("", "");
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("code", is("1200"))
                    .body("message", is("UserName and Password required."));
    }

    @Test
    @Tag("rgtNotCharacters")
    @DisplayName("Create an account with a password that does not contain special characters return - 400")
    public void createAnAccountWithAPasswordThatDoesNotContainSpecialCharacters() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn(userName, "passwd");
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .body("code", is("1300"))
                    .body("message", is(
                                    "Passwords must have at least one non alphanumeric character, " +
                                    "one digit (\'0\'-\'9\'), one uppercase (\'A\'-\'Z\'), " +
                                    "one lowercase (\'a\'-\'z\'), one special character and " +
                                    "Password must be eight characters or longer."));
    }

    @Test
    @Tag("rgtExistingAccount")
    @DisplayName("Create an account with the same data as an existing account return - 400")
    public void createAnAccountWithTheSameDataAsAnExistingAccount() {
        RegisterUserDamn UserForRegistration = new RegisterUserDamn("user1", password);
        registerUser(UserForRegistration)
                .then()
                    .statusCode(HttpStatus.SC_NOT_ACCEPTABLE)
                    .body("code", is("1204"))
                    .body("message", is("User exists!"));
    }

}