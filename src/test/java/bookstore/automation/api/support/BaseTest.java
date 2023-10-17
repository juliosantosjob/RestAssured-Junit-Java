package bookstore.automation.api.support;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;

import static bookstore.automation.api.support.PropertiesSupport.getSecret;

public class BaseTest {
    protected static final Faker faker = new Faker();
    private final String baseUrl = getSecret("BASE_URL");
    private final Long timeout = 3000L;

    @BeforeEach
    public void setup() {

        RestAssured.baseURI = baseUrl;

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
