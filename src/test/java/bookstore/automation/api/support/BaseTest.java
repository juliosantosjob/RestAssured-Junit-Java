package bookstore.automation.api.support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;


import static bookstore.automation.api.support.PropertiesSupport.getProp;

/**
 * @author Julio C. Santos
 */

public class BaseTest {
    private static final String baseUrl = getProp("BASE_URL");
    private static final String basePath = getProp("BASE_PATH");
    private static final Long timeout = 3000L;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = basePath;

        // Definindo ContentTypeJson como padrão para todos os testes
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = reqBuilder.build();

        // Define um timeout maximo para a execução de cada chamada
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(timeout));
        RestAssured.responseSpecification = resBuilder.build();

        // Habilita os logs da chamada caso ocorra algum erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}
