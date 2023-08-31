package ru.ibs.framework.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ibs.framework.core.utils.MyAllureListener;
import ru.ibs.framework.core.utils.PropsConst;
import ru.ibs.framework.ui.managers.DriverManager;
import ru.ibs.framework.ui.managers.InitManager;
import ru.ibs.framework.ui.managers.PageManager;
import ru.ibs.framework.ui.managers.TestPropManager;

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
