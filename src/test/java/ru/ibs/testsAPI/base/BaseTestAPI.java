package ru.ibs.testsAPI.base;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import ru.ibs.framework.data.ProductDTO;
import ru.ibs.framework.utils.Product;

import java.util.List;

import static ru.ibs.testsAPI.specification.Specification.installSpecification;

@Slf4j
public class BaseTestAPI {

    @Step("Отправка запроса и получение ответа")
    @BeforeAll
    protected static void initSpec() {
        installSpecification();
    }
    /**
     * Метод проверяет конкректную строку с параметрами,
     * в таблице
     *
     * @param productList - полученная таблица
     * @param expectedProduct - искомый продукт
     */
    @Step("Проверка строки с параметрами {expectedProduct}")
    public static void checkingTableRows(List<ProductDTO> productList, Product expectedProduct) {
        log.info(String.format("Проверка строки с параметрами %s", expectedProduct.toString()));

        Assertions.assertTrue(productList.stream().anyMatch(
                (product) -> {return product.getName().equals(expectedProduct.getName()) &&
                        product.getType().equals(expectedProduct.getTypeForAPI()) &&
                        product.getExotic().equals(expectedProduct.isExotic());}
                        ),
        "Наименование не найдено");

    }




}
