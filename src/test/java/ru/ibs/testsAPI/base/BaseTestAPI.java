package ru.ibs.testsAPI.base;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.api.CheckAPIContext;
import ru.ibs.framework.core.utils.PropsConst;

import static ru.ibs.framework.api.Specification.installSpecification;

@Slf4j
public class BaseTestAPI extends CheckAPIContext{


    @BeforeAll
    protected static void initSpec() {
        installSpecification();
        connectionPool = JdbcConnectionPool.create(
                propManager.getProperty(PropsConst.JDBC_URL),
                propManager.getProperty(PropsConst.JDBC_USER),
                propManager.getProperty(PropsConst.JDBC_PASSWORD));
    }


}
