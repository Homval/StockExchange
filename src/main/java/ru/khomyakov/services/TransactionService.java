package ru.khomyakov.services;

import ru.khomyakov.domain.ClientAccount;
import ru.khomyakov.domain.StockRequest;

public class TransactionService {

    public static void executeTransaction(ClientAccount seller, ClientAccount buyer, StockRequest request) {
        seller.setDollarAccount(seller.getDollarAccount() + request.getStockPrice() * request.getStockAmount());
        buyer.setDollarAccount(buyer.getDollarAccount() - request.getStockPrice() * request.getStockAmount());
        switch (request.getStockName()) {
            case ("A") :
                seller.setShareA(seller.getShareA() - request.getStockAmount());
                buyer.setShareA(buyer.getShareA() + request.getStockAmount());

        }
    }
}
