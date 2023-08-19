package ru.ibs.tests;

import org.junit.jupiter.api.Test;
import ru.ibs.framework.pages.MainPage;
import ru.ibs.tests.base.BaseTests;

public class CheckProductDisplayTest extends BaseTests {

    @Test
    public void checkProductDisplayed() {
        pageManager.getPage(MainPage.class)
                .waitting();
    }
}
