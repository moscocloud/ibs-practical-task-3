package ru.ibs.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.pages.MainPage;
import ru.ibs.tests.base.BaseTests;

public class CheckAddProductTest extends BaseTests {

    @Test
    @Tag("@ТС-002")
    @DisplayName("Проверка функционала добавления товаров")
    public void checkAddProduct() {
        pageManager.getPage(MainPage.class)
                .checkOpenPage()
                .clickButtonAdd()
                .checkModalWindowIsDisplayed()
                .inputFieldName("Манго")
                .waitting();


    }
}
