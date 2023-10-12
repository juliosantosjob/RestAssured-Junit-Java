package bookstore.automation.api.tests;

import bookstore.automation.api.support.BaseTest;
import com.github.javafaker.Faker;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static bookstore.automation.api.payloads.DeleteAccountsPld.deletUser;
import static bookstore.automation.api.support.PropertiesSupport.getSecret;
import static bookstore.automation.api.utils.RequestsHelper.getUserId;
import static bookstore.automation.api.utils.RequestsHelper.getTokenLoginUer;
import static org.hamcrest.Matchers.*;

/**
 * @author Julio C. Santos
 */

public class DeleteAccountTest extends BaseTest {
    private static final Faker faker = new Faker();
    private static final String userName = faker.address().firstName();
    private static final String password = getSecret("PASSWORD");
    private static String token;
    private static String accountId;


    @BeforeEach
    public void hookBefore() {
        accountId = getUserId(userName, password);
        token = getTokenLoginUer(userName, password);
    }

    @Test
    @DisplayName("Delete account return - 204")
    public void deleteAccount() {
        deletUser(token, accountId).then().
                statusCode(HttpStatus.SC_NO_CONTENT).
                body(is(""));
    }

    @Test
    @DisplayName("Delet non-existent user")
    public void deletNonExistentUser() {
        accountId = "invalid_user_id";

        deletUser(token, accountId).then().
                statusCode(HttpStatus.SC_OK).
                body(
                        "code", is("1207"),
                        "message", is("User Id not correct!")
                );
    }

}