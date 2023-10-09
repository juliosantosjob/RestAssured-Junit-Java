package bookstore.automation.api.support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import static bookstore.automation.api.support.PropertiesSupport.propLoad;

/**
 * @author Julio C. Santos
 */

public class BaseApi {
    // configurações principais do projeto
    private static final String baseUrl = propLoad("BASE_URL");
    private static final Long timeout = 3000L;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.basePath = "/Account/v1";

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
