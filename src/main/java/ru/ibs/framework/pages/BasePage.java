package ru.ibs.framework.pages;

import io.qameta.allure.Attachment;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.framework.managers.DriverManager;
import ru.ibs.framework.managers.PageManager;

import java.util.List;

public class BasePage {
    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);
    protected PageManager pageManager = PageManager.getInstance();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }


    // ----- Ниже приведены методы которые я посчитал вынести из ------//
    // ----- пейджей в общий класс от которого они наследуются   ------//

    /**
     * Метод для перебора наименований продуктов и поиска конкретного продукта
     *
     * @param name             - Наименование продукта
     * @param productNamesList - Список продуктов
     */
    protected void findNameProduct(String name, List<WebElement> productNamesList) {
        for (WebElement productName : productNamesList) {
            if (productName.getText().equals(name)) {
                Assertions.assertEquals(name, productName.getText());
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }

    /** Метод добавляет ожидание до момента пока
     * элимент не станет кликабельным
     *
     * @param webElement элемент
     * @return WebElement
     */
    protected WebElement waitToBeClickable(WebElement webElement) {
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**Метод делает скриншот и аттачит его в алюр отчет
     *
     * @return Screenshot
     */
    @Attachment(value = "Screenshot", type = "image/png", fileExtension = "png")
    public byte[] screenshot() {
        return ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    /**Метод ожидает пока страница прогрузится и станет стабильной
     *
     * @param maxWaitMillis Время ожидания
     * @param pollDelimitres времям промежутка ожидания
     */
    protected void waitStabilityPage(int maxWaitMillis, int pollDelimitres){
        double startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() < startTime + maxWaitMillis) {
            String prevState = driverManager.getDriver().getPageSource();
            waitting(pollDelimitres);
            if (prevState.equals(driverManager.getDriver().getPageSource())) {
                return;
            }
        }
    }

    /**
     * Ожидалка
     */
    public static void waitting(int milliSecond) {
        try {
            Thread.sleep(milliSecond);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
