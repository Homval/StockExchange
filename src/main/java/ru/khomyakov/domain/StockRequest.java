package ru.khomyakov.domain;

import java.util.Objects;

public class StockRequest {
    private final ClientAccount client;
    private final String stockAction;
    private final StockNames stockName;
    private final int stockPrice;
    private final int stockAmount;

    public StockRequest(ClientAccount client, String stockAction, StockNames stockName, int stockPrice, int stockAmount) {
        this.client = client;
        this.stockAction = stockAction;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockAmount = stockAmount;
    }

    public ClientAccount getClient() {
        return client;
    }

    public StockNames getStockName() {
        return stockName;
    }

    public int getStockPrice() {
        return stockPrice;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockRequest that = (StockRequest) o;
        return stockPrice == that.stockPrice && stockAmount == that.stockAmount && stockName == that.stockName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, stockPrice, stockAmount);
    }
}
