package ru.ibs.testsUI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.pages.MainPage;
import ru.ibs.testsUI.base.BaseTests;

import static ru.ibs.framework.utils.Product.TOMATO;

public class CheckProductDisplayTest extends BaseTests {

    @Test
    @Tag("@ТС-001")
    @DisplayName("Проверка валидации отображения товаров в списке")
    public void checkProductDisplayed() {
        pageManager.getPage(MainPage.class)
                .checkOpenPage()
                .checkTableDisplayed()
                .checkTitlesTables()
                .checkTableRowWithParam(TOMATO)
                .checkButtonAdd();
    }
}
