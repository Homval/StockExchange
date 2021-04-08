package ru.khomyakov.domain;

public class StockRequest {
    private final ClientAccount client;
    private final StockAction stockAction;
    private final String stockName;
    private final int stockPrice;
    private final int stockAmount;

    public StockRequest(ClientAccount client, StockAction stockAction, String stockName, int stockPrice, int stockAmount) {
        this.client = client;
        this.stockAction = stockAction;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
        this.stockAmount = stockAmount;
    }

    public ClientAccount getClient() {
        return client;
    }

    public StockAction getStockAction() {
        return stockAction;
    }

    public String getStockName() {
        return stockName;
    }

    public int getStockPrice() {
        return stockPrice;
    }

    public int getStockAmount() {
        return stockAmount;
    }
}
