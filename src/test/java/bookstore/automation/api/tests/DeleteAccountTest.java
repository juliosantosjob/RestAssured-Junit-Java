package bookstore.automation.api.tests;

import bookstore.automation.api.domain.LoginDmn;
import bookstore.automation.api.support.BaseTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static bookstore.automation.api.payloads.DeleteAccountsPld.deleteUser;
import static bookstore.automation.api.payloads.LoginPld.loginUser;
import static bookstore.automation.api.payloads.RegisterUserPld.registerUser;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

@Tag("regression")
public class DeleteAccountTest extends BaseTest {
    private final String userName = faker.address().firstName();
    private final String password = getSecret("PASSWORD");
    private final LoginDmn user = new LoginDmn(userName, password);

    private String accessToken;
    private String accountId;

    @BeforeEach
    public void hookBefore() {
        accountId = registerUser(user)
                .then()
                    .extract()
                    .path("userID")
                    .toString();
        accessToken = loginUser(user)
                .then()
                    .extract()
                    .path("token")
                    .toString();
    }

    @Test
    @Tag("dltAccount")
    @DisplayName("Delete account return - 204")
    public void deleteAccount() {
        deleteUser(accessToken, accountId)
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT)
                    .body(is(""));
    }

    @Test
    @Tag("dltNonExistent")
    @DisplayName("Delet non-existent user return code - 1207")
    public void deletNonExistentUser() {
        accountId = "invalid_user_id";
        deleteUser(accessToken, accountId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("code", is("1207"))
                    .body("message", is("User Id not correct!"));
    }

}