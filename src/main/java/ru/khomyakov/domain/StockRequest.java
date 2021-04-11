package ru.khomyakov.domain;

import java.util.Objects;

public class StockRequest {
    private final String clientName;
    private final String stockAction;
    private final StockNames stockName;
    private final int stockPrice;
    private final int stockAmount;

    public StockRequest(String clientName, String stockAction, StockNames stockName, int stockPrice, int stockAmount) {
        this.clientName = clientName;
        this.stockAction = stockAction;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockAmount = stockAmount;
    }

    public String getClientName() {
        return clientName;
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

    public String getStockAction() {
        return stockAction;
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
