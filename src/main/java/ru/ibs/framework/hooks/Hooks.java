package ru.ibs.framework.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ibs.framework.UI.managers.DriverManager;
import ru.ibs.framework.UI.managers.InitManager;
import ru.ibs.framework.UI.managers.PageManager;
import ru.ibs.framework.UI.managers.TestPropManager;
import ru.ibs.framework.utils.MyAllureListener;
import ru.ibs.framework.utils.PropsConst;

@ExtendWith(MyAllureListener.class)
public class Hooks {
    private DriverManager driverManager = DriverManager.getInstance();
    private TestPropManager propManager = TestPropManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();


    @Before
    public void before() {
        InitManager.initFrame();
        driverManager.getDriver().get(propManager.getProperty(PropsConst.BASE_URL));
    }

    @After
    public void after() {
        InitManager.quitFrame();
        pageManager.clearPages();
    }
}
