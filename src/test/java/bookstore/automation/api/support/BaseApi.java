package bookstore.automation.api.support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;


import static bookstore.automation.api.support.PropertiesSupport.getProperty;

/**
 * @author Julio C. Santos
 */

public class BaseApi {
    private static final String baseUrl = getProperty("BASE_URL");
    private static final String basePath = getProperty("BASE_PATH");
    private static final Long timeout = 3000L;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = baseUrl;
<<<<<<< HEAD
        RestAssured.basePath = basePath;
=======
        RestAssured.basePath = "/Account/v1";
>>>>>>> a4a5a5572fe816115e75b86921ca5e4407e21517

        // Definindo ContentTypeJson como padrão para todas as chamadas
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = reqBuilder.build();

        // Define um timeout para a execução de cada chamada
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(timeout));
        RestAssured.responseSpecification = resBuilder.build();

        // Habilita os logs da chamada caso ocorra um erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
