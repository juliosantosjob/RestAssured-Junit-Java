package com.bookstore.support;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.path.json.JsonPath;

import java.util.HashMap;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.junit.BeforeClass;

import static com.bookstore.support.PropertiesSupport.propLoad;

public class BaseApi {
    private static final String baseUrl = propLoad("BASE_URL");
    private static final Long timeout = 3000L;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;

        // Definindo ContentTypeJson como padrão para todas as chamadas
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setContentType(ContentType.JSON);
        RestAssured.requestSpecification = reqBuilder.build();

        // Definindo 2 segundos como tempo maximo de espera para cada chamada
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
        resBuilder.expectResponseTime(Matchers.lessThan(timeout));
        RestAssured.responseSpecification = resBuilder.build();

        // Habilita os logs da chamada caso ocorra um erro
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

}