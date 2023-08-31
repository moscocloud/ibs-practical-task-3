package ru.ibs.framework.cucumberSteps;

import io.cucumber.java.ru.И;

import static ru.ibs.framework.db.CheckDBContext.*;

public class DataBaseSteps{

    @И("Подключение и установка соединения с базой данных")
    public void openConnectionDBCucumber() {
        createConnectionDB();
    }

    @И("^Отправка SELECT запроса - \"(.+)\" и проверка что ответ не пустой$")
    public void sendSelectSQLAndCheckResponse(String sqlQuery) {
        sendSelectAndProcessing(sqlQuery);
    }

    @И("^Отправка INSERT запроса - \"(.+)\"$")
    public void sendInsertSql(String sqlQuery) {
        sendInsertQuery(sqlQuery);
    }

    @И("^Отправка SELECT запроса - \"(.+)\" и поиск строки с параметрами: Наименование -  \"(.+)\", Тип -  \"(.+)\", Экзотический -  \"(.+)\" в ответе$")
    public void sendSelectAndCheckTableRow(String sqlQuery, String name, String type, String isExotic){
        sendSelectAndChechTableRows(sqlQuery, name, type, isExotic);
    }

    @И("^Отправка DELETE запроса - \"(.+)\"$")
    public void sendDeleteSql(String sqlQuery) {
        sendDeleteQuery(sqlQuery);
    }

    @И("Закрытие соединения с базой данных")
    public void сloseConnectionDBCucumber() {
        closeConnectionDB();
    }

}
