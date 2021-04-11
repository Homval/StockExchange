package ru.khomyakov;

import org.junit.Before;
import org.junit.Test;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockNames;
import ru.khomyakov.domain.StockRequest;
import ru.khomyakov.exceptions.WrongStockRequestException;
import ru.khomyakov.services.OrderRequestsService;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OrderRequestServiceTest {
    String[] params;

    @Before
    public void init() {
        params = new String[] {"A", "b", "A", "10", "10"};
    }

    @Test
    public void check_correct_parameters() throws WrongStockRequestException {
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test(expected = WrongStockRequestException.class)
    public void check_incorrect_param_with_null_client_name() throws WrongStockRequestException {
        params[0] = null;
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test(expected = WrongStockRequestException.class)
    public void check_incorrect_params_with_empty_client_name() throws WrongStockRequestException {
        params[0] = "";
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test(expected = WrongStockRequestException.class)
    public void check_incorrect_action_param() throws WrongStockRequestException {
        params[1] = "w";
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test(expected = WrongStockRequestException.class)
    public void check_incorrect_array_length() throws WrongStockRequestException {
        params = new String[] {"A", "b", "A", "10"};
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_incorrect_stock_name_param() throws WrongStockRequestException {
        params[2] = "w";
        OrderRequestsService.areRequestParametersCorrect(params);
    }

    @Test
    public void check_is_request_in_list_if_false() {
        StockRequest request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> list = new LinkedList<>();
        list.add(request);

        assertFalse(OrderRequestsService.isRequestAvailableForExecute(request, list));
    }

    @Test
    public void check_is_request_in_list_if_true() {
        StockRequest request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest request1 = new StockRequest("B", "b", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> list = new LinkedList<>();
        list.add(request);

        assertTrue(OrderRequestsService.isRequestAvailableForExecute(request1, list));
    }

    @Test
    public void check_is_request_with_other_stock_name_in_list() {
        StockRequest request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest request2 = new StockRequest("A", "b", StockNames.valueOf("B"), 10, 10);
        List<StockRequest> list = new LinkedList<>();
        list.add(request2);

        assertFalse(OrderRequestsService.isRequestAvailableForExecute(request, list));
    }

    @Test
    public void check_is_request_with_other_price_in_list() {
        StockRequest request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest request2 = new StockRequest("B", "s", StockNames.valueOf("A"), 15, 10);
        List<StockRequest> list = new LinkedList<>();
        list.add(request2);

        assertFalse(OrderRequestsService.isRequestAvailableForExecute(request, list));
    }

    @Test
    public void check_is_request_with_other_amount_in_list() {
        StockRequest request = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest request2 = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 15);
        List<StockRequest> list = new LinkedList<>();
        list.add(request2);

        assertFalse(OrderRequestsService.isRequestAvailableForExecute(request, list));
    }

    @Test
    public void check_execute_correct_request_for_buy() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest sellerRequest = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        sellersList.add(sellerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(buyersList, sellersList, buyerRequest, clients);

        assertEquals(0, sellersList.size());

    }

    @Test
    public void check_execute_incorrect_request_for_buy() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest sellerRequest = new StockRequest("A", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        sellersList.add(sellerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(buyersList, sellersList, buyerRequest, clients);

        assertTrue(sellersList.size() == 1 && buyersList.size() == 1);

    }

    @Test
    public void check_execute_incorrect_request_for_sell() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest sellerRequest = new StockRequest("A", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        buyersList.add(buyerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(sellersList, buyersList, sellerRequest, clients);


        assertTrue(sellersList.size() == 1 && buyersList.size() == 1);

    }

    @Test
    public void check_add_in_list_correct_request_with_different_price() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("A"), 15, 10);
        StockRequest sellerRequest = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        sellersList.add(sellerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(buyersList, sellersList, buyerRequest, clients);

        assertTrue(sellersList.size() == 1 && buyersList.size() == 1);

    }

    @Test
    public void check_add_in_list_correct_request_with_different_amount() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 15);
        StockRequest sellerRequest = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        sellersList.add(sellerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(buyersList, sellersList, buyerRequest, clients);

        assertTrue(sellersList.size() == 1 && buyersList.size() == 1);

    }

    @Test
    public void check_add_in_list_correct_request_with_different_stock_name() {
        StockRequest buyerRequest = new StockRequest("A", "b", StockNames.valueOf("B"), 10, 10);
        StockRequest sellerRequest = new StockRequest("B", "s", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        List<StockRequest> sellersList = new LinkedList<>();
        sellersList.add(sellerRequest);
        Map<String, ClientAccount> clients = new HashMap<>();
        ClientAccount buyer = new ClientAccount("A", 1000, 1000, 1000, 1000, 1000);
        ClientAccount seller = new ClientAccount("B", 100, 100, 100, 100, 100);
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);

        OrderRequestsService.addToListOrExecuteRequest(buyersList, sellersList, buyerRequest, clients);

        assertTrue(sellersList.size() == 1 && buyersList.size() == 1);

    }

    @Test
    public void remove_executed_request() {
        StockRequest buyerRequest1 = new StockRequest("A", "b", StockNames.valueOf("A"), 10, 10);
        StockRequest buyerRequest = new StockRequest("B", "b", StockNames.valueOf("A"), 10, 10);
        List<StockRequest> buyersList = new LinkedList<>();
        buyersList.add(buyerRequest);
        buyersList.add(buyerRequest1);

        OrderRequestsService.remove(buyersList, buyerRequest1);

        assertEquals(1, buyersList.size());

    }
}
