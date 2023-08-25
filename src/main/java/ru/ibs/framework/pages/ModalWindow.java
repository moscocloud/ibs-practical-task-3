package ru.ibs.framework.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.ibs.framework.utils.Product;

@Slf4j
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
        log.info("Проверка открытия и содержания модального окна");

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
     * Метод совмещает методы по вводу полей: Наименование
     * Тип, экзотический.
     *
     * @param product - Продукт
     * @return ModalWindow
     */
    public ModalWindow fillFieldsProduct(Product product) {
        inputFieldName(product.getName());
        choiceType(product.getType(), product.getTypeForAPI());
        inputCheckbox(product.isExotic());
        return this;
    }

    /**
     * Метод вводит значение в поле Наименование
     * <p>
     *
     * @param name - наименование продукта
     * @return ModalWindow
     */
    @Step("Ввод в поле наименование \"{name}\"")
    public ModalWindow inputFieldName(String name) {
        log.info(String.format("Ввод в поле наименование значения \"%s\"", name));

        fieldName.sendKeys(name);
        Assertions.assertEquals(fieldName.getAttribute("value"), name,
                "Элемент несоответствует введенному значению");
        return this;
    }

    /**
     * Метод выбирает тип продукта.
     * Первый клик раскрывает дропдаун.
     * Потом находится тип переданный в метод и
     * второй клик происходит с выборо данного элемента
     * <p>
     *
     * @param type тип продукта
     * @return ModalWindow
     */
    @Step("Выбор типа из данных \"{type}\"")
    public ModalWindow choiceType(String type, String typeAPI) {
        log.info(String.format("Выбор типа \"%s\"", type));

        String typeName = type.substring(0, 1).toUpperCase() +
                type.substring(1).toLowerCase();

        String optionSelect = String.format("./option[text()='%s']", typeName);

        if (typeName.matches("Овощ|Фрукт")) {
            selectType.click();
            selectType.findElement(By.xpath(optionSelect)).click();

            Assertions.assertEquals(selectType.getAttribute("value"), typeAPI,
                    "Элемент несоответствует введенному значению");

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
     *
     * @param isExotic значение чекбокса true/false
     * @return ModalWindow
     */
    @Step("Выбор значения чекбокса из данных \"{isExotic}\"")
    public ModalWindow inputCheckbox(boolean isExotic) {
        log.info(String.format("Выбор значения чекбокса \"%s\"", isExotic));

        if (isExotic) {
            checkbox.click();
            Assertions.assertTrue(Boolean.parseBoolean(checkbox.getAttribute("checked")),
                    "Чекбокс выбран неверно");
            return this;
        } else {
            Assertions.assertFalse(Boolean.parseBoolean(checkbox.getAttribute("checked")),
                    "Чекбокс выбран неверно");
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
        log.info("Сохранение продукта");

        waitToBeClickable(saveButton).click();
        return pageManager.getPage(MainPage.class);
    }


}
