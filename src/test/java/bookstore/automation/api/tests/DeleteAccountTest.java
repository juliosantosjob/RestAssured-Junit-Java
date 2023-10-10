package bookstore.automation.api.tests;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static bookstore.automation.api.utils.RequestsHelper.getToken;

public class DeleteAccountTest {
    private static String token;
    private static String accountId;

    @BeforeEach
    public void hook() {
        token = getToken("QA_2", "Test@123");
    }

    @Test
    @DisplayName("Delete account return - 200")
    public void deleteAccount() {
        RestAssured.
                given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/User/" + accountId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}