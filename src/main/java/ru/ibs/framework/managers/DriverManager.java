package ru.ibs.framework.managers;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverManager {
    private WebDriver driver;
    private static DriverManager INSTANCE = null;

    /**
     * Проверка инициализации Дравер менаджера чтобы не создавать его повторно
     */
    public static DriverManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    /**
     * Проверка уже инициализированого драйвера, чтобы не создавать его еще раз
     */
    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }

    /**
     * Инициалиация драйвера через библеотеку Bonigarcia
     */
    private void initDriver() {
        driver = WebDriverManager.chromedriver().create();
    }

    /**
     * Закрытие драйвера, используется в BaseTest для того чтобы можно было запускать несколько тестов подряд
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
