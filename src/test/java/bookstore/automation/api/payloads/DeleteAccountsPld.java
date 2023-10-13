package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteAccountsPld {

    public static Response deletUser(String token, String accountId) {
        return RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/Account/v1/User/" + accountId);
    }

}