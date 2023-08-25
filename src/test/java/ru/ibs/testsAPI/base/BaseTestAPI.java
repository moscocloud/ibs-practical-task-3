package ru.ibs.testsAPI.base;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.data.ProductDTO;
import ru.ibs.framework.utils.Product;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.ibs.testsAPI.specification.Specification.installSpecification;

@Slf4j
public class BaseTestAPI {


    @BeforeAll
    protected static void initSpec() {
        installSpecification();
    }

    /**
     * Метод отправляет запрос и возвращает лист продуктов
     *
     * @return List<ProductDTO>
     */
    @Step("Получение списка продуктов")
    protected List<ProductDTO> getProductList() {
        log.info("Получение списка продуктов");

        return given()
                .when()
                .get("/api/food")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList(".", ProductDTO.class);
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
    public static void checkingTableRows(List<ProductDTO> productList, Product expectedProduct) {
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
