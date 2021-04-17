package ru.khomyakov;

import org.junit.Before;
import org.junit.Test;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.services.TransactionService;
import static org.junit.Assert.*;


public class TransactionServiceTest {
    StockRequest sellerRequest;
    StockRequest buyerRequest;

    @Before
    public void init() {
        sellerRequest = new StockRequest("B", "b", StockNames.valueOf("A"), 10, 10);
        buyerRequest = new StockRequest("B", "b", StockNames.valueOf("A"), 10, 10);
        ClientAccount buyer = new ClientAccount("B", 0, 0, 0, 0, 0);
        ClientAccount seller = new ClientAccount("A", 0, 0, 0, 0, 0);
        App.clients.put(sellerRequest.getClientName(), seller);
        App.clients.put(buyerRequest.getClientName(), buyer);
    }

    @Test
    public void check_correct_transaction_for_seller() {
        TransactionService.executeTransaction(sellerRequest, buyerRequest);

        ClientAccount sellerResult = new ClientAccount(sellerRequest.getClientName(), 100, -10, 0, 0, 0);

        assertEquals(App.clients.get(sellerRequest.getClientName()), sellerResult);
    }

    @Test
    public void check_correct_transaction_for_buyer() {
        TransactionService.executeTransaction(sellerRequest, buyerRequest);

        ClientAccount buyerResult = new ClientAccount(buyerRequest.getClientName(), -100, 10, 0, 0, 0);

        assertEquals(App.clients.get(buyerRequest.getClientName()), buyerResult);
    }

}
