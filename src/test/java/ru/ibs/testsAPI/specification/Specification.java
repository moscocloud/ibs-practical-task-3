package ru.ibs.testsAPI.specification;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.ibs.framework.managers.TestPropManager;
import ru.ibs.framework.utils.PropsConst;

import static io.restassured.RestAssured.given;

@Slf4j
public class Specification {
    private static TestPropManager propManager = TestPropManager.getInstance();
    private static Cookies cookies = getCookies();

    /**
     * Получение ID сессии через куки.
     *
     * @return куки
     */
    public static Cookies getCookies() {
        log.info("Получение куки");
        return given()
                .when()
                .get("/api/food")
                .getDetailedCookies();
    }

    /** Настройка спецификации отправки запроса
     * @return RequestSpecification
     */
    public static RequestSpecification requestSpec() {
        log.info("Отправка запроса");
        return new RequestSpecBuilder()
                .setBaseUri(propManager.getProperty(PropsConst.BASE_URL_API))
                .setContentType("application/json")
                .addCookies(cookies)
                .build();
    }

    /** Проверка ответа
     * @return ResponseSpecification
     */
    public static ResponseSpecification responseSpec() {
        log.info("Получение ответа");
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    /**
     * Инициализация спецификаций
     */
    public static void installSpecification() {
        RestAssured.requestSpecification = requestSpec();
        RestAssured.responseSpecification = responseSpec();
    }
}
