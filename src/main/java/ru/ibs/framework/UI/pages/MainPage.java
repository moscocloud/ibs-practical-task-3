package ru.ibs.framework.UI.pages;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ibs.framework.utils.Product;

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
    public MainPage checkOpenPage() {
        log.info("Проверка открытия сайта по заголовку");

        Assertions.assertTrue(title.isDisplayed(), "Заголовок таблицы не найден");
        return this;
    }

    /**
     * Метод проверяет отображение таблицы товаров
     *
     * @return MainPage
     */
    public MainPage checkTableDisplayed() {
        log.info("Проверка наличия таблицы товаров");

        screenshot();
        Assertions.assertTrue(table.isDisplayed(), "Таблица не найден");
        return this;
    }

    /**
     * Метод проверяет заголовки таблицы товаров
     *
     * @return MainPage
     */
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
     * @param product - Продукт
     * @return MainPage
     */
    public MainPage checkTableRowWithParam(Product product) {
        String name = product.getName();
        String type = product.getType();
        String exotic = String.valueOf(product.isExotic());

        log.info(String.format("Проверка строки таблицы с параметрами: " +
                "Наименование:\"%s\", тип:\"%s\", экзотический: \"%s\"", name, type, exotic));

        waitStabilityPage(3000, 1000);

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
    public ModalWindow clickButtonAdd() {
        log.info("Нажатие на кнопку \"Добавить\"");

        waitToBeClickable(button).click();
        return pageManager.getPage(ModalWindow.class);
    }

    public MainPage checkTableRowWithParamForCucumber(String name, String type, String exotic) {
        log.info(String.format("Проверка строки таблицы с параметрами: " +
                "Наименование:\"%s\", тип:\"%s\", экзотический: \"%s\"", name, type, exotic));

        waitStabilityPage(3000, 1000);

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
}

