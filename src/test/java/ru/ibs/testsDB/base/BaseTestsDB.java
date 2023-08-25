package ru.ibs.testsDB.base;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.data.ProductData;
import ru.ibs.framework.managers.TestPropManager;
import ru.ibs.framework.utils.Product;
import ru.ibs.framework.utils.PropsConst;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseTestsDB {

    private static TestPropManager propManager = TestPropManager.getInstance();
    static JdbcConnectionPool connectionPool;
    protected static Connection connection;


    @BeforeAll
    public static void beforeTestsDB() {
        connectionPool = JdbcConnectionPool.create(
                propManager.getProperty(PropsConst.JDBC_URL),
                propManager.getProperty(PropsConst.JDBC_USER),
                propManager.getProperty(PropsConst.JDBC_PASSWORD));
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * Метод устанавливает соединение с базой данных
     */
    @Step("Установка соединения с базой данных")
    protected static void createConnectionDB() {
        log.info("Соединение с БД...");
        try {
            connection = connectionPool.getConnection();
            log.info("Соединение с БД установлено");
        } catch (SQLException e) {
            log.info("ОШИБКА соединения с БД");
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод отправляет SELECT SQL запрос в базу данных
     *
     * @param sqlQuery - строка SQL запроса
     * @return ResultSet
     */
    @Step("Отправка SELECT SQL запроса в БД")
    protected static ResultSet sendingSelectSQLQuery(String sqlQuery) {
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try {
            return connection.createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Проверка что ответ не пустой")
    protected static void checkRespIsNotEmpty(List<ProductData> productList) {
        Assertions.assertFalse(productList.isEmpty());
    }

    @Step("Отправка INSERT SQL запроса в БД")
    protected static boolean sendingSQLQuery(String sqlQuery) {
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try {
            return connection.createStatement().execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод парсит полученный ответ от БД типа
     * ResultSet. Преобразовывает данные в тип
     * ProductData
     *
     * @param resultSet - ответ от БД
     * @return список данных типа ProductData
     */
    @Step("Получение данных из ответа на SQL запрос")
    protected static List<ProductData> getDataFromResp(ResultSet resultSet) {
        log.info("Получение данных из ответа...");

        List<ProductData> productList = new ArrayList<>();
        try {
            log.info("Преобразование данных...");
            while (resultSet.next()) {
                productList.add(new ProductData<>(
                        resultSet.getString("FOOD_NAME"),
                        resultSet.getString("FOOD_TYPE"),
                        resultSet.getBoolean("FOOD_EXOTIC")));
            }
            log.info("Данные преобразованы");
            return productList;
        } catch (SQLException e) {
            log.info("Ошибка преобразования");
            throw new RuntimeException();
        }
    }

    /**
     * Метод переберает лист и проверяет есть ли искомая строка в
     * списке
     *
     * @param productList     список продуктов
     * @param expectedProduct искомая строка
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

