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
        log.info("Соединение с БД установлено");
    }

    /**
     * Отправка SELECT запроса,
     * преобразование данных и проверка что ответ не пустой
     */
    @Step("Отправка SELECT запроса преобразование ответа и проверка что ответ не пустой")
    protected static void sendSelectAndCheckNotEmpty(String sqlQuery) {

        List<ProductData> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                log.info("Получение данных из ответа...");
                while (resultSet.next()) {
                    productList.add(new ProductData<>(
                            resultSet.getString("FOOD_NAME"),
                            resultSet.getString("FOOD_TYPE"),
                            resultSet.getBoolean("FOOD_EXOTIC")));
                }
            }
            log.info("Данные получены и преобразованы");
            Assertions.assertFalse(productList.isEmpty(), "Ответ пустой");
        } catch (SQLException e) {

            log.info("Ошибка преобразования");
            e.printStackTrace();
        }
    }

    /**
     * Отправка INSERT запроса
     * @param sqlQuery запрос
     * @return true/false
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
     * Отправка селект запроса и поиск отправленой строки в таблице
     * @param sqlQuery запрос
     * @param expectedProduct добавленный продукт
     */
    @Step("Отправка SELECT запроса преобразование ответа и поиск отправленой строки в таблице")
    protected static void sendSelectAndCheckTableRow(String sqlQuery, Product expectedProduct) {

        List<ProductData> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
            try (ResultSet resultSet = statement.executeQuery(sqlQuery)) {

                log.info("Получение данных из ответа...");
                while (resultSet.next()) {
                    productList.add(new ProductData<>(
                            resultSet.getString("FOOD_NAME"),
                            resultSet.getString("FOOD_TYPE"),
                            resultSet.getBoolean("FOOD_EXOTIC")));
                }
            }

            log.info("Данные получены и преобразованы");
            log.info(String.format("Проверка строки с параметрами %s", expectedProduct.toString()));
            Assertions.assertTrue(productList.stream().anyMatch(
                    (product) -> {
                        return product.getName().equals(expectedProduct.getName()) &&
                                product.getType().equals(expectedProduct.getTypeForAPI()) &&
                                product.getExotic().equals(expectedProduct.isExotic());
                    }), "Строка не найдена");
        } catch (SQLException e) {

            log.info("Ошибка преобразования");
            e.printStackTrace();
        }
    }

    @Step("Отправка DELETE SQL запроса в БД")
    protected static boolean sendingDeleteSQLQuery(String sqlQuery) {

        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try (Statement statement = connection.createStatement()) {
            return statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Соединение через DataSource
     */
    protected static JdbcDataSource createConnectionWithDataSourse() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(propManager.getProperty(PropsConst.JDBC_USER));
        dataSource.setUser(propManager.getProperty(PropsConst.JDBC_USER));
        dataSource.setPassword(propManager.getProperty(PropsConst.JDBC_PASSWORD));
        return dataSource;
    }


}
