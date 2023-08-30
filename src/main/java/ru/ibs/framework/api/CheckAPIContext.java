package ru.ibs.framework.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import ru.ibs.framework.core.utils.Product;
import ru.ibs.framework.core.utils.ProductData;

import java.util.List;

import static io.restassured.RestAssured.given;

@Slf4j
public class CheckAPIContext {
    /**
     * Метод отправляет запрос и возвращает лист продуктов
     *
     * @return List<ProductData>
     */
    @Step("Получение списка продуктов")
    protected List<ProductData> getProductList() {
        log.info("Получение списка продуктов");

        return given()
                .when()
                .get("/api/food")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList(".", ProductData.class);
    }

    /**Метод отправляет пост запрос с телом product
     *
     * @param product тело пост запроса
     */
    @Step("Отправка POST запроса с телом {product}")
    protected void sendPost(Product product) {
        log.info("Отправка POST запроса");

        Response response = given()
                .body(product.postBody())
                .when()
                .post("/api/food");
    }

    @Step("Сброс тестовых данных")
    protected void resetDataBase() {
        given()
                .when()
                .post("/api/data/reset");
    }
    /**
     * Метод проверяет конкректную строку с параметрами,
     * в таблице
     *
     * @param productList     - полученная таблица
     * @param expectedProduct - искомый продукт
     */
    @Step("Проверка строки с параметрами {expectedProduct}")
    protected static void checkingTableRows(List<ProductData> productList, Product expectedProduct) {
        log.info(String.format("Проверка строки с параметрами %s", expectedProduct.toString()));

        Assertions.assertTrue(productList.stream().anyMatch(
                        (product) -> {
                            return product.getName().equals(expectedProduct.getName()) &&
                                    product.getType().equals(expectedProduct.getTypeForAPI()) &&
                                    product.getExotic().equals(expectedProduct.isExotic());
                        }
                ),
                "Наименование не найдено");
    }

}
