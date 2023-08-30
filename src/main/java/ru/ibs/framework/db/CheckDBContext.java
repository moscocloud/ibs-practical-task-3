package ru.ibs.framework.db;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import ru.ibs.framework.core.TestPropManager;
import ru.ibs.framework.core.utils.Product;
import ru.ibs.framework.core.utils.ProductData;
import ru.ibs.framework.core.utils.PropsConst;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CheckDBContext {

    protected static TestPropManager propManager = TestPropManager.getInstance();
    protected static JdbcConnectionPool connectionPool;
    protected static Connection connection;

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
     * Метод отправляет запрост, после преобразует
     * полученные данные в лист данных типа ProductData
     * и проверяет что лист данных не пустой
     *
     * @param sqlQuery - запрос
     * @return Лист данных
     */
    protected static List<ProductData> requestTransformationCheck(String sqlQuery) {
        ResultSet response = sendingSelectSQLQuery(sqlQuery);
        List<ProductData> products = getDataFromResp(response);
        checkRespIsNotEmpty(products);
        return products;
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод отправляет INSERT SQL запрос в базу данных
     *
     * @param sqlQuery - строка SQL запроса
     * @return ResultSet
     */
    @Step("Отправка INSERT SQL запроса в БД")
    protected static boolean sendingSQLQuery(String sqlQuery) {
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try (Statement statement = connection.createStatement()) {
            return statement.execute(sqlQuery);
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
        try (ResultSet res = resultSet) {
            log.info("Преобразование данных...");
            while (res.next()) {
                productList.add(new ProductData<>(
                        res.getString("FOOD_NAME"),
                        res.getString("FOOD_TYPE"),
                        res.getBoolean("FOOD_EXOTIC")));
            }
            log.info("Данные преобразованы");
            log.info(String.format("Полученные данные: %s", productList.toString()));
            return productList;
        } catch (SQLException e) {
            log.info("Ошибка преобразования");
            throw new RuntimeException();
        }
    }

    /**
     * Метод проверяет данные полученные в ответе не пустые
     *
     * @param productList - получечнные в ответе данные
     */
    @Step("Проверка что ответ не пустой")
    protected static void checkRespIsNotEmpty(List<ProductData> productList) {
        Assertions.assertFalse(productList.isEmpty());
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

    /**
     * Соединение через DataSource
     *
     */
    protected static JdbcDataSource createConnectionWithDataSourse() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(propManager.getProperty(PropsConst.JDBC_USER));
        dataSource.setUser(propManager.getProperty(PropsConst.JDBC_USER));
        dataSource.setPassword(propManager.getProperty(PropsConst.JDBC_PASSWORD));
        return dataSource;
    }


}
