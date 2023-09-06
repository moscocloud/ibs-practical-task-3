package ru.ibs.framework.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.Assertions;
import ru.ibs.framework.core.TestPropManager;
import ru.ibs.framework.core.utils.Product;
import ru.ibs.framework.core.utils.ProductData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@Slf4j
public class CheckAPIContext {

    protected static TestPropManager propManager = TestPropManager.getInstance();
    protected static JdbcConnectionPool connectionPool;

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

    /**
     * Метод отправляет пост запрос с телом product
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

    /**
     * Метод проверяет отсутствие продукта который хотим добавиться,
     * в таблице
     *
     * @param productList     - полученная таблица
     * @param expectedProduct - искомый продукт
     */
    @Step("Проверка строки с параметрами {expectedProduct}")
    protected static void checkThatTableHasNotProduct(List<ProductData> productList, Product expectedProduct) {
        log.info(String.format("Проверка отсутствия строки с параметрами %s", expectedProduct.toString()));

        Assertions.assertFalse(productList.stream().anyMatch(
                        (product) -> {
                            return product.getName().equals(expectedProduct.getName()) &&
                                    product.getType().equals(expectedProduct.getTypeForAPI()) &&
                                    product.getExotic().equals(expectedProduct.isExotic());
                        }
                ),
                "Продукт найден");
        log.info(String.format("Строка %s в таблице не найдена", expectedProduct.toString()));
    }


    @Step("Проверка добавления товара через JDBC")
    protected static void checkProductWithJDBC(String SelectSQL, Product expectedProduct) {

        List<ProductData> productList = new ArrayList<>();

        log.info("Установка соединения с базой данных...");
        try (Connection connection = connectionPool.getConnection()) {

            log.info("Отправляю запрос в базу данных...");
            try (Statement statement = connection.createStatement()) {

                log.info("Преобразование данных ответа...");
                try (ResultSet resultSet = statement.executeQuery(SelectSQL)) {
                    while (resultSet.next()) {
                        productList.add(new ProductData<>(
                                resultSet.getString("FOOD_NAME"),
                                resultSet.getString("FOOD_TYPE"),
                                resultSet.getBoolean("FOOD_EXOTIC")
                        ));
                    }
                }
                log.info("Данные преобразованы");
                log.info(String.format("Полученные данные: %s", productList.toString()));

                log.info(String.format("Проверка строки с параметрами %s", expectedProduct.toString()));
                Assertions.assertTrue(productList.stream().anyMatch(
                                (product) -> {
                                    return product.getName().equals(expectedProduct.getName()) &&
                                            product.getType().equals(expectedProduct.getTypeForAPI()) &&
                                            product.getExotic().equals(expectedProduct.isExotic());
                                })
                        , String.format("Элемент %s не найден" , expectedProduct.toString()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Step("Удаление через JDBC")
    protected static void deleteWithJDBC(String DeleteSQL) {

        List<ProductData> productList = new ArrayList<>();

        log.info("Установка соединения с базой данных...");
        try (Connection connection = connectionPool.getConnection()) {

            log.info("Отправляю запрос на удаление в базу данных...");
            try (Statement statement = connection.createStatement()) {
                statement.execute(DeleteSQL);
                log.info("Строка удалена");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
