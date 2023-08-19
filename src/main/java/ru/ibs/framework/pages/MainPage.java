package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {
    @FindBy(xpath = "//div[@class='container-fluid']/h5")
    private WebElement title;

    @FindBy(xpath = "//table[@class='table']")
    private WebElement table;

    @FindBy(xpath = "//th[2]")
    private WebElement titleProductName;
    @FindBy(xpath = "//th[3]")
    private WebElement titleProductType;
    @FindBy(xpath = "//th[4]")
    private WebElement titleIsProductExotic;
    @FindBy(xpath = "//td[1]")
    private List<WebElement> productNames;

    @FindBy(xpath = "//button[@data-toggle='modal']")
    private WebElement button;


    @Step("Проверка открытия сайта по заголовку")
    public MainPage checkOpenPage() {
        title.isDisplayed();
        return this;
    }

    @Step("Проверка наличия таблицы")
    public MainPage checkTableDisplayed() {
        table.isDisplayed();
        return this;
    }

    @Step("Проверка заголовков таблицы")
    public MainPage checkTitlesTables() {
        Assertions.assertEquals("Наименование", titleProductName.getText());
        Assertions.assertEquals("Тип", titleProductType.getText());
        Assertions.assertEquals("Экзотический", titleIsProductExotic.getText());
        return this;
    }

    /**
     * Метод проверяет конкректную строку с параметрами,
     * внутри использует метод #findNameProduct
     *
     * @param name   - Наименование продукта
     * @param type   - Тип продукта
     * @param exotic - Экзотический продукт или нет
     * @return MainPage
     */
    @Step("Проверка строки таблицы с параметрами: Наименование:\"{name}\", тип:\"{type}\", экзотический: \"{exotic}\"")
    public MainPage checkTableRowWithParam(String name, String type, Boolean exotic) {

        findNameProduct(name, productNames);

        String typeXpath = String.format("//td[text()='%s']/following-sibling::td[1]", name);
        String exoticXpath = String.format("//td[text()='%s']/following-sibling::td[2]", name);

        WebElement productType = table.findElement(By.xpath(typeXpath));
        WebElement productIsExotic = table.findElement(By.xpath(exoticXpath));

        Assertions.assertEquals(type, productType.getText());
        Assertions.assertEquals(exotic.toString(), productIsExotic.getText());

        return this;
    }

    @Step("Проверка кнопки \"Добавить\"")
    public MainPage checkButtonAdd() {
        Assertions.assertTrue(waitToBeClickable(button).isDisplayed(), "Кнопка \"Добавить\" отсутствует");
        return this;
    }


}

