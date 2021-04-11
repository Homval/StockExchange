package ru.khomyakov.services;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockRequest;

import java.util.Map;

public class TransactionService {

//  executing transaction between seller and buyer
    public static void executeTransaction(String sellerName, String buyerName, StockRequest request, Map<String, ClientAccount> clients) {
        ClientAccount seller = clients.get(sellerName);
        ClientAccount buyer = clients.get(buyerName);
        seller.setDollarAccount(seller.getDollarAccount() + request.getStockPrice() * request.getStockAmount());
        seller.getShareAmount().compute(request.getStockName(), (k, v) -> v == null? -1 * request.getStockAmount() :  v - request.getStockAmount());
        buyer.setDollarAccount(buyer.getDollarAccount() - request.getStockPrice() * request.getStockAmount());
        buyer.getShareAmount().compute(request.getStockName(), (k, v) -> v == null? request.getStockAmount() :  v + request.getStockAmount());
        clients.put(seller.getClientName(), seller);
        clients.put(buyer.getClientName(), buyer);
    }
}
