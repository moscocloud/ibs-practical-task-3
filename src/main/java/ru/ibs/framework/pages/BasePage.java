package ru.ibs.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ibs.framework.managers.DriverManager;

import java.util.List;

public class BasePage {
    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

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

    protected WebElement waitToBeClickable(WebElement webElement) {
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    /**
     * Ожидалка
     */
    public void waitting() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
