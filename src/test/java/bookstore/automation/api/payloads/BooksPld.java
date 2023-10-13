package bookstore.automation.api.payloads;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class BooksPld {

    public static Response getBookList() {
        return RestAssured
                .given()
                .when()
                .get("/BookStore/v1/Books");
    }
}
