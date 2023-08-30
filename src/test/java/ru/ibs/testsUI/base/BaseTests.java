package ru.ibs.testsUI.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ibs.framework.ui.managers.DriverManager;;
import ru.ibs.framework.ui.managers.InitManager;
import ru.ibs.framework.ui.managers.PageManager;
import ru.ibs.framework.core.TestPropManager;
import ru.ibs.framework.core.utils.MyAllureListener;
import ru.ibs.framework.core.utils.PropsConst;

@ExtendWith(MyAllureListener.class)
public class BaseTests {

    private DriverManager driverManager = DriverManager.getInstance();
    private TestPropManager propManager = TestPropManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();

    @BeforeAll
    public static void beforeClass() {
        InitManager.initFrame();
    }

    @BeforeEach
    public void before() {
        driverManager.getDriver().get(propManager.getProperty(PropsConst.BASE_URL));
    }

    @AfterEach
    public void after() {
        InitManager.quitFrame();
        pageManager.clearPages();
    }
}
