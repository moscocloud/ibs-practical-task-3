package ru.ibs.testsAPI;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.data.ProductDTO;
import ru.ibs.framework.utils.Product;
import ru.ibs.testsAPI.base.BaseTestAPI;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CheckAddProductAPITest extends BaseTestAPI {

    @Test
    @Tag("@ТС-003")
    @DisplayName("Проверка функционала добавления товаров через API")
    public void checkAddProduct() {
        List<ProductDTO> products = given()
                .when()
                .get("/api/food")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList(".", ProductDTO.class);
        checkingTableRows(products, Product.TOMATO);
    }
}
