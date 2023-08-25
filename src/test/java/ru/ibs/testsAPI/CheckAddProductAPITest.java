package ru.ibs.testsAPI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.data.ProductDTO;
import ru.ibs.testsAPI.base.BaseTestAPI;

import java.util.List;

import static ru.ibs.framework.utils.Product.MANGO;

public class CheckAddProductAPITest extends BaseTestAPI {

    @Test
    @Tag("@ТС-003")
    @DisplayName("Проверка функционала добавления товаров через API")
    public void checkAddProduct() {
        sendPost(MANGO);
        List<ProductDTO> products = getProductList();
        checkingTableRows(products, MANGO);
        resetDataBase();
    }
}
