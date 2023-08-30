package ru.ibs.testsDB.base;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.db.CheckDBContext;
import ru.ibs.framework.core.utils.PropsConst;

import java.sql.SQLException;

@Slf4j
public class BaseTestsDB extends CheckDBContext{

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


}

