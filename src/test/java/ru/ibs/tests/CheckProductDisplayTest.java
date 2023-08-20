package ru.ibs.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.pages.MainPage;
import ru.ibs.tests.base.BaseTests;

import static ru.ibs.framework.utils.Product.TOMATO;

public class CheckProductDisplayTest extends BaseTests {

    @Test
    @Tag("@ТС-001")
    @DisplayName("Проверка валидации отображения товаров в списке")
    public void checkProductDisplayed() {
        pageManager.getPage(MainPage.class)
                .checkTableDisplayed()
                .checkTitlesTables()
                .checkTableRowWithParam(TOMATO.getName(), TOMATO.getType(), TOMATO.isExotic())
                .checkButtonAdd();
    }
}
