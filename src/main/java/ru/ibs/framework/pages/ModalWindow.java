package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
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

    /**
     * Метод проверяет содержание модального окна
     *
     * @return ModalWindow
     */
    @Step("Проверка открытия и содержания модального окна")
    public ModalWindow checkModalWindowIsDisplayed() {
        Assertions.assertTrue(modalWindow.isDisplayed());
        Assertions.assertEquals("Добавление товара", modalTitle.getText());
        Assertions.assertEquals("Наименование", labelName.getText());
        Assertions.assertEquals("Тип", labelType.getText());
        Assertions.assertEquals("Экзотический", labelExotic.getText());
        Assertions.assertEquals("Сохранить", saveButton.getText());
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


}
