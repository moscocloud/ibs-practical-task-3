package ru.ibs.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.pages.MainPage;
import ru.ibs.tests.base.BaseTests;

public class CheckProductDisplayTest extends BaseTests {

    @Test
    @DisplayName("Проверка валидации отображения товаров в списке")
    public void checkProductDisplayed() {
        pageManager.getPage(MainPage.class)
                .checkTableDisplayed()
                .checkTitlesTables()
                .checkTableRowWithParam("Помидор", "Овощ", false)
                .checkButtonAdd();


    }
}
