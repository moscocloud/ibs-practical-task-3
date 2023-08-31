package ru.ibs.framework.cucumberSteps;

import io.cucumber.java.ru.И;
import ru.ibs.framework.ui.managers.PageManager;
import ru.ibs.framework.ui.pages.ModalWindow;

public class ModalWindowSteps {
    PageManager pageManager = PageManager.getInstance();

    @И("^Проверка открытия и содержания модального окна$")
    public void checkModalWindowIsDisplayed() {
        pageManager.getPage(ModalWindow.class).checkModalWindowIsDisplayed();
    }

    @И("^Ввод в поле наименование \"(.+)\"$")
    public void inputFieldName(String name) {
        pageManager.getPage(ModalWindow.class).inputFieldName(name);
    }

    @И("^Выбор типа продукта \"(.+)\"$")
    public void choiceTypeForCucumber(String type) {
        pageManager.getPage(ModalWindow.class).choiceTypeForCucumber(type);
    }

    @И("^Выбор значения чекбокса \"(.+)\"$")
    public void inputCheckbox(boolean isExotic) {
        pageManager.getPage(ModalWindow.class).inputCheckbox(isExotic);
    }
    @И("Сохранение продукта")
    public void saveProduct() {
        pageManager.getPage(ModalWindow.class).saveProduct();
    }
}
