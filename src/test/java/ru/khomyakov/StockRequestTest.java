package ru.khomyakov;

import org.junit.Test;
import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;

import static org.junit.Assert.*;

public class StockRequestTest {

    @Test
    public void is_the_same_requests_equal() {
        StockRequest request1 = new StockRequest("B", "s", StockNames.valueOf("A"), 15, 10);
        StockRequest request2 = new StockRequest("A", "b", StockNames.valueOf("A"), 15, 10);

        assertEquals(request1, request2);
    }

    @Test
    public void is_requests_with_different_prices_not_equal() {
        StockRequest request1 = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 10);
        StockRequest request2 = new StockRequest("A", "b", StockNames.valueOf("A"), 15, 10);

        assertNotEquals(request1, request2);
    }

    @Test
    public void is_requests_with_different_amounts_not_equal() {
        StockRequest request1 = new StockRequest("B", "s", StockNames.valueOf("A"), 15, 15);
        StockRequest request2 = new StockRequest("A", "b", StockNames.valueOf("A"), 15, 10);

        assertNotEquals(request1, request2);
    }

    @Test
    public void is_requests_with_different_stock_names_not_equal() {
        StockRequest request1 = new StockRequest("B", "s", StockNames.valueOf("B"), 15, 10);
        StockRequest request2 = new StockRequest("A", "b", StockNames.valueOf("A"), 15, 10);

        assertNotEquals(request1, request2);
    }
}
