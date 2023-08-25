package ru.ibs.testsDB;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ibs.framework.data.ProductData;
import ru.ibs.testsDB.base.BaseTestsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static ru.ibs.framework.utils.Product.MANGO;
import static ru.ibs.testsDB.sql.Query.*;

@Slf4j
public class CheckAddProductDBTest extends BaseTestsDB {

    @Test
    @Tag("@ТС-005")
    @DisplayName("Проверка товаров в DB")
    public void checkAddProductDBTest() throws SQLException {
        createConnectionDB();

        ResultSet respOnSelectQuery = sendingSelectSQLQuery(SELECT_QUERY);
        List<ProductData> products = getDataFromResp(respOnSelectQuery);
        checkRespIsNotEmpty(products);

        sendingSQLQuery(INSERT_QUERY);
        ResultSet respOnSelectBeforeInsert = sendingSelectSQLQuery(SELECT_QUERY);
        List<ProductData> productsBeforeInsert = getDataFromResp(respOnSelectBeforeInsert);
        checkingTableRows(productsBeforeInsert, MANGO);

        sendingSQLQuery(DELETE_QUERY);
    }

}

