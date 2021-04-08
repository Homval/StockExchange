package ru.khomyakov.services;

import ru.khomyakov.App;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockRequest;

public class TransactionService {

//  executing transaction between seller and buyer
    public static void executeTransaction(ClientAccount seller, ClientAccount buyer, StockRequest request) {
        seller.setDollarAccount(seller.getDollarAccount() + request.getStockPrice() * request.getStockAmount());
        seller.getShareAmount().compute(request.getStockName(), (k, v) -> v == null? request.getStockAmount() :  v - request.getStockAmount());
        buyer.setDollarAccount(buyer.getDollarAccount() - request.getStockPrice() * request.getStockAmount());
        buyer.getShareAmount().compute(request.getStockName(), (k, v) -> v == null? request.getStockAmount() :  v + request.getStockAmount());
        App.clients.put(seller.getClientName(), seller);
        App.clients.put(buyer.getClientName(), buyer);
    }
}
