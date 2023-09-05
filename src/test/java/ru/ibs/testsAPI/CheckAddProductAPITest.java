package ru.ibs.testsAPI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.core.utils.ProductData;
import ru.ibs.testsAPI.base.BaseTestAPI;

import java.util.List;

import static ru.ibs.framework.core.utils.Product.MANGO;
import static ru.ibs.framework.core.utils.SQLQuery.SELECT_QUERY;

public class CheckAddProductAPITest extends BaseTestAPI {

    @Test
    @Tag("@ТС-004")
    @DisplayName("Проверка функционала добавления товаров через API")
    public void checkAddProduct() {
        sendPost(MANGO);
        List<ProductData> products = getProductList();
        checkingTableRows(products, MANGO);
//      проверка добавления товара через jdbc
        checkProductAndDeleteWithJDBC(SELECT_QUERY, MANGO);

        resetDataBase();
    }
}
