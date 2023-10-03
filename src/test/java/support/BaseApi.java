package support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;

public class BaseApi {
    private static final String baseUrl = "https://bookstore.toolsqa.com";
    private static final Long timeout = 2000L;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;

        // Definindo ContentTypeJson como padrão para todas as chamadas
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = reqBuilder.build();

        // Definindo o tempo maximo de espera para chamada
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(timeout));
        RestAssured.responseSpecification = resBuilder.build();

        // Habilita os logs da chamada caso ocorra um erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}