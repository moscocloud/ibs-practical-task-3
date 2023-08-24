package ru.ibs.testsAPI.specification;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.ibs.framework.managers.TestPropManager;
import ru.ibs.framework.utils.PropsConst;

@Slf4j
public class Specification  {
    private static TestPropManager propManager = TestPropManager.getInstance();

    @Step("Отправка запроса")
    public static RequestSpecification requestSpec() {
        log.info("Отправка запроса");
        return new RequestSpecBuilder()
                .setBaseUri(propManager.getProperty(PropsConst.BASE_URL_API))
                .setContentType("application/json")
                .build();
    }

    @Step("Получение ответа")
    public static ResponseSpecification responseSpec() {
        log.info("Получение ответа");
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static void installSpecification() {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.responseSpecification = responseSpec();
    }

}
