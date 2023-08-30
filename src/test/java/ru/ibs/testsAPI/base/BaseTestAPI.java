package ru.ibs.testsAPI.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.api.CheckAPIContext;

import static ru.ibs.framework.api.Specification.installSpecification;

@Slf4j
public class BaseTestAPI extends CheckAPIContext{


    @BeforeAll
    protected static void initSpec() {
        installSpecification();
    }


}
