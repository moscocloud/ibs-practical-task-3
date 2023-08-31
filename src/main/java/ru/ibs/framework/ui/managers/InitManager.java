package ru.ibs.framework.ui.managers;

import ru.ibs.framework.core.utils.PropsConst;

import java.util.concurrent.TimeUnit;

public class InitManager {

    private static final DriverManager driverManager = DriverManager.getInstance();
    private static final TestPropManager prop = TestPropManager.getInstance();


    /**
     * Раскрытие окна браузера на полный экран, установка ожидания
     */
    public static void initFrame() {
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().manage().timeouts().implicitlyWait(Integer.parseInt(prop.getProperty(PropsConst.IMPLICITY_WAIT)), TimeUnit.SECONDS);
        driverManager.getDriver().manage().timeouts().pageLoadTimeout(Integer.parseInt(prop.getProperty(PropsConst.PAGE_LOAD_TIMEOUT)), TimeUnit.SECONDS);
    }

    /**
     * Закрытие сессии с браузером
     */
    public static void quitFrame() {
        driverManager.quitDriver();
    }
}
