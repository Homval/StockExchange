package ru.khomyakov;

import org.junit.Before;
import org.junit.Test;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.services.TransactionService;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class TransactionServiceTest {
    String sellerName;
    String buyerName;
    StockRequest request;
    Map<String, ClientAccount> clients;

    @Before
    public void init() {
        sellerName = "A";
        buyerName = "B";
        request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("A", 100, 100, 100, 100, 100);
        clients.put(sellerName, seller);
        clients.put(buyerName, buyer);
    }

    @Test
    public void check_correct_transaction_for_seller() {
        TransactionService.executeTransaction(sellerName, buyerName, request, clients);

        ClientAccount account1 = clients.get(sellerName);

        ClientAccount sellerResult = new ClientAccount("A", 200, 90, 100, 100, 100);

        assertEquals(account1, sellerResult);
    }

    @Test
    public void check_correct_transaction_for_buyer() {
        TransactionService.executeTransaction(sellerName, buyerName, request, clients);

        ClientAccount account2 = clients.get(buyerName);

        ClientAccount buyerResult = new ClientAccount("A", 900, 1010, 1000, 1000, 1000);

        assertEquals(account2, buyerResult);
    }

}
