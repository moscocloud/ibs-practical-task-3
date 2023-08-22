package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Slf4j
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

    /**
     * Метод проверяет открылся ли сайт,
     * путем проверки отображения заголовка
     *
     * @return MainPage
     */
    @Step("Проверка открытия сайта по заголовку")
    public MainPage checkOpenPage() {
        log.info("Проверка открытия сайта по заголовку");

        title.isDisplayed();
        return this;
    }

    /**
     * Метод проверяет отображение таблицы товаров
     *
     * @return MainPage
     */
    @Step("Проверка наличия таблицы товаров")
    public MainPage checkTableDisplayed() {
        log.info("Проверка наличия таблицы товаров");

        screenshot();
        table.isDisplayed();
        return this;
    }

    /**
     * Метод проверяет заголовки таблицы товаров
     *
     * @return MainPage
     */
    @Step("Проверка заголовков таблицы товаров")
    public MainPage checkTitlesTables() {
        log.info("Проверка заголовков таблицы товаров");

        Assertions.assertAll(
                () -> Assertions.assertEquals("Наименование", titleProductName.getText(),
                        "Заголовок таблицы \"Наименование\" не найден"),
                () -> Assertions.assertEquals("Тип", titleProductType.getText(),
                        "Заголовок таблицы \"Тип\" не найден"),
                () -> Assertions.assertEquals("Экзотический", titleIsProductExotic.getText(),
                        "Заголовок таблицы \"Экзотический\" не найден")
        );
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
    @Step("Проверка строки таблицы с параметрами: Наименование: \"{name}\", тип: \"{type}\", экзотический: \"{exotic}\"")
    public MainPage checkTableRowWithParam(String name, String type, Boolean exotic) {
        log.info(String.format("Проверка строки таблицы с параметрами: " +
                "Наименование:\"%s\", тип:\"%s\", экзотический: \"%s\"", name, type, exotic));

        waitStabilityPage(3000,1000);

        findNameProduct(name, productNames);

        String typeXpath = String.format(".//td[text()='%s']/following-sibling::td[1]", name);
        String exoticXpath = String.format(".//td[text()='%s']/following-sibling::td[2]", name);

        WebElement productType = table.findElement(By.xpath(typeXpath));
        WebElement productIsExotic = table.findElement(By.xpath(exoticXpath));
        Assertions.assertAll(
                () -> Assertions.assertEquals(type, productType.getText(),
                        String.format("Тип %s у элимента %s не найден", type, name)),
                () -> Assertions.assertEquals(exotic.toString(), productIsExotic.getText(),
                        String.format("Параметр \"Экзотический\" = %s у элимента %s не найден", exotic, name)));

        return this;
    }

    /**
     * Метод проверяет отображение
     * активной кнопки добавить
     *
     * @return MainPage
     */
    @Step("Проверка кнопки \"Добавить\"")
    public MainPage checkButtonAdd() {
        log.info("Проверка кнопки \"Добавить\"");

        Assertions.assertTrue(waitToBeClickable(button).isDisplayed(), "Кнопка \"Добавить\" отсутствует");
        return this;
    }

    /**
     * Метод кликает на кнопку добавить
     *
     * @return ModalWindow
     */
    @Step("Нажатие на кнопку \"Добавить\"")
    public ModalWindow clickButtonAdd() {
        log.info("Нажатие на кнопку \"Добавить\"");

        waitToBeClickable(button).click();
        return pageManager.getPage(ModalWindow.class);
    }


}

