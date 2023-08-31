package ru.ibs.framework.db;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.Assertions;
import ru.ibs.framework.core.data.ProductData;
import ru.ibs.framework.core.utils.PropsConst;
import ru.ibs.framework.ui.managers.TestPropManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CheckDBContext {

    protected static TestPropManager propManager = TestPropManager.getInstance();;
    protected static JdbcConnectionPool connectionPool;
    protected static Connection connection;

    public static void createConnectionDB(){
        log.info("Соединение с БД...");
        connectionPool = JdbcConnectionPool.create(
                propManager.getProperty(PropsConst.JDBC_URL),
                propManager.getProperty(PropsConst.JDBC_USER),
                propManager.getProperty(PropsConst.JDBC_PASSWORD));
        try {
            connection = connectionPool.getConnection();
            log.info("Соединение установлено");
        } catch (SQLException e) {
            log.info("Ошибка соединения с БД");
            throw new RuntimeException(e);
        }
    }

    public static void sendSelectAndProcessing(String sqlQuery){
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));

        List<ProductData> productList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            log.info("Получение данных из ответа на запрос...");
            while (resultSet.next()) {
                productList.add(new ProductData<>(
                    resultSet.getString("FOOD_NAME"),
                    resultSet.getString("FOOD_TYPE"),
                    resultSet.getBoolean("FOOD_EXOTIC")));

            }
            log.info("Данные преобразованы");
            log.info(String.format("Полученные данные: %s", productList.toString()));
            log.info("Проверка что ответ не пустой");
            Assertions.assertFalse(productList.isEmpty());

        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public static void sendInsertQuery(String sqlQuery){
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendSelectAndChechTableRows(String sqlQuery, String name, String type, String isExotic){
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));

        List<ProductData> productList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            log.info("Получение данных из ответа на запрос...");

            while (resultSet.next()) {
                productList.add(new ProductData<>(
                        resultSet.getString("FOOD_NAME"),
                        resultSet.getString("FOOD_TYPE"),
                        resultSet.getString("FOOD_EXOTIC")));

            }
            log.info("Данные преобразованы");
            log.info(String.format("Полученные данные: %s", productList.toString()));
            log.info("Проверка что ответ не пустой");

            Assertions.assertFalse(productList.isEmpty());

            log.info(String.format("Проверка строки с параметрами: Наименование - %s," +
                            " Тип - %s," +
                            " Экзотический - %s ",
                    name, type, isExotic));

            Assertions.assertTrue(productList.stream().anyMatch(
                            (product) -> {
                                return product.getName().equals(name) &&
                                        product.getType().equals(type) &&
                                        product.getExotic().equals(isExotic);
                            }
                    ),
                    "Наименование не найдено");
        } catch (SQLException e){
            throw new RuntimeException();
        }
    }

    public static void sendDeleteQuery(String sqlQuery){
        log.info(String.format("Отправка запроса \"%s\" в базу данных", sqlQuery));
        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnectionDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ;
    }




}
