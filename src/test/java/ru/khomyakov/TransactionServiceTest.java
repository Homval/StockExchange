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
        request = new StockRequest("B", "b", StockNames.valueOf("A"), 10, 10);
        clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("B", 0, 0, 0, 0, 0);
        ClientAccount seller = new ClientAccount("A", 0, 0, 0, 0, 0);
        clients.put(sellerName, seller);
        clients.put(buyerName, buyer);
    }

    @Test
    public void check_correct_transaction_for_seller() {
        TransactionService.executeTransaction(sellerName, buyerName, request, clients);

        ClientAccount account1 = clients.get(sellerName);

        ClientAccount sellerResult = new ClientAccount("A", 100, -10, 0, 0, 0);

        assertEquals(account1, sellerResult);
    }

    @Test
    public void check_correct_transaction_for_buyer() {
        TransactionService.executeTransaction(sellerName, buyerName, request, clients);

        ClientAccount account2 = clients.get(buyerName);

        ClientAccount buyerResult = new ClientAccount("B", -100, 10, 0, 0, 0);

        assertEquals(account2, buyerResult);
    }

}
