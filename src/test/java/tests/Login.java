package tests;

import support.BaseApi;

import org.json.simple.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import jdk.jfr.Description;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author Julio C. Santos
 */

public class Login extends BaseApi {

        @Test
        @Description("Login sucessfully")
        public void login_successfully() {
                requestBody.put("userName", "QA_2");
                requestBody.put("password", "Test@123");
                requestBodyJson = new JSONObject(requestBody);

                httpRequest = RestAssured.given();
                response = httpRequest.body(requestBodyJson.toJSONString()).post("/Account/v1/GenerateToken");
                jsonPath = response.jsonPath();

                assertTrue(response.getBody().asString().contains("token"));
                assertTrue(response.getBody().asString().contains("expires"));

                assertEquals(200, response.getStatusCode());
                assertEquals("Success", jsonPath.get("status").toString());
                assertEquals("User authorized successfully.", jsonPath.get("result").toString());
        }

        @Test
        @Description("Login sucessfully")
        public void login_success() {

                String responseBody = "{\"userName\": \"QA_2\", \"password\": \"Test@123\"}";
                RestAssured
                                .given()
                                .body(responseBody)
                                .when()
                                .post("/Account/v1/GenerateToken")
                                .then()
                                .statusCode(200)
                                .body(
                                                containsString("token"),
                                                containsString("expires"))
                                .body(
                                                "status", equalTo("Success"),
                                                "result", equalTo("User authorized successfully."));
        }

}