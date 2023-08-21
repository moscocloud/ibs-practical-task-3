package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ModalWindow extends BasePage {
    @FindBy(xpath = "//div[@class='modal-content']")
    private WebElement modalWindow;

    @FindBy(xpath = "//h5[@class='modal-title']")
    private WebElement modalTitle;

    @FindBy(xpath = "//label[@for='name']")
    private WebElement labelName;

    @FindBy(xpath = "//label[@for='type']")
    private WebElement labelType;

    @FindBy(xpath = "//label[@for='exotic']")
    private WebElement labelExotic;

    @FindBy(xpath = "//button[@id='save']")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@id='name']")
    private WebElement fieldName;

    @FindBy(xpath = "//select[@name='type']")
    private WebElement selectType;

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement checkbox;

    /**
     * Метод проверяет содержание модального окна
     *
     * @return ModalWindow
     */
    @Step("Проверка открытия и содержания модального окна")
    public ModalWindow checkModalWindowIsDisplayed() {
        waitStabilityPage(5000, 500);
        Assertions.assertAll("Checked modal",
                () -> Assertions.assertTrue(modalWindow.isDisplayed(), "Модальное окно не найдено"),
                () -> Assertions.assertEquals("Добавление товара", modalTitle.getText(),
                        "Заголовок \"Добавление товара\" не найден"),
                () -> Assertions.assertEquals("Наименование", labelName.getText(),
                        "Заголовок \"Наименование\" не найден"),
                () -> Assertions.assertEquals("Тип", labelType.getText(),
                        "Заголовок \"Тип\" не найден"),
                () -> Assertions.assertEquals("Экзотический", labelExotic.getText(),
                        "Заголовок чекбокса \"Экзотический\" не найден"),
                () -> Assertions.assertEquals("Сохранить", saveButton.getText(),
                        "Кнопка \"Сохранить\" не найдена"));
        screenshot();
        return this;
    }

    /**
     * Метод вводит значение в поле Наименование
     * <p>
     * По-хорошему следует проверить правильное заполнение полей
     * но в данной реализации сайта, не нашел изменения в HTML
     * во время ввода
     *
     * @param productName - значение для ввода
     * @return ModalWindow
     */
    @Step("Ввод в поле наименование значения \"{productName}\"")
    public ModalWindow inputFieldName(String productName) {
        fieldName.sendKeys(productName);
        return this;
    }

    /**
     * Метод выбирает тип продукта.
     * <p>
     * По-хорошему следует проверить появление выпадающего
     * списка после нажатия на select, но в данной реализации сайта,
     * не нашел изменения в HTML после нажатия на Select
     *
     * @param typeName название типа продукта
     * @return ModalWindow
     */
    @Step("Выбор типа \"{typeName}\"")
    public ModalWindow inputSelectType(String typeName) {

        typeName = typeName.substring(0, 1).toUpperCase() +
                typeName.substring(1).toLowerCase();

        String optionSelect = String.format("./option[text()='%s']", typeName);


        if (typeName.matches("Овощ|Фрукт")) {
            selectType.click();
            selectType.findElement(By.xpath(optionSelect)).click();
            return this;
        } else {
            Assertions.fail("Данный тип продуктов не найден");
            return this;
        }
    }

    /**
     * Метод выбирает чекбокс в зависимости
     * от переданных параметров
     * <p>
     * По-хорошему следует проверить что чекбокс
     * становится активным после нажатия, но в данной реализации
     * сайта, не нашел изменения в HTML после нажатия на Checkbox
     *
     * @param isCheckboxActive значение ЧекБокса
     * @return ModalWindow
     */
    @Step("Выбор значения чекбокса \"{checkboxStatus}\"")
    public ModalWindow inputCheckbox(boolean isCheckboxActive) {
        if (isCheckboxActive == true) {
            checkbox.click();
            return this;
        } else {
            return this;
        }
    }

    /**
     * Метод сохраняет продукт
     *
     * @return MainPage
     */
    @Step("Сохранение продукта")
    public MainPage saveProduct() {
        waitToBeClickable(saveButton).click();
        return pageManager.getPage(MainPage.class);
    }


}
