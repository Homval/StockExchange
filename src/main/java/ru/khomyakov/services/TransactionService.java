package ru.khomyakov.services;

import ru.khomyakov.App;
import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockRequest;

public class TransactionService {

//  executing transaction between seller and buyer
    public static void executeTransaction(StockRequest sellerRequest, StockRequest buyerRequest) {
        ClientAccount seller = App.clients.get(sellerRequest.getClientName());
        ClientAccount buyer = App.clients.get(buyerRequest.getClientName());
        seller.setDollarAccount(seller.getDollarAccount() + sellerRequest.getStockPrice() * sellerRequest.getStockAmount());
        seller.getShareAmount().compute(sellerRequest.getStockName(), (k, v) -> v == null? -1 * sellerRequest.getStockAmount() :  v - sellerRequest.getStockAmount());
        buyer.setDollarAccount(buyer.getDollarAccount() - sellerRequest.getStockPrice() * sellerRequest.getStockAmount());
        buyer.getShareAmount().compute(sellerRequest.getStockName(), (k, v) -> v == null? sellerRequest.getStockAmount() :  v + sellerRequest.getStockAmount());
        App.clients.put(seller.getClientName(), seller);
        App.clients.put(buyer.getClientName(), buyer);
    }
}

