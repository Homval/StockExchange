package ru.khomyakov.domain;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientAccount {
    private final String clientName;
    private int dollarAccount;
    private final Map<StockNames, Integer> shareAmount = new HashMap<>();

    public ClientAccount(String clientName, int dollarAccount, int shareA, int shareB, int shareC, int shareD) {
        this.clientName = clientName;
        this.dollarAccount = dollarAccount;
        shareAmount.put(StockNames.A, shareA);
        shareAmount.put(StockNames.B, shareB);
        shareAmount.put(StockNames.C, shareC);
        shareAmount.put(StockNames.D, shareD);
    }

    public String getClientName() {
        return clientName;
    }

    public int getDollarAccount() {
        return dollarAccount;
    }

    public void setDollarAccount(int dollarAccount) {
        this.dollarAccount = dollarAccount;
    }

    public Map<StockNames, Integer> getShareAmount() {
        return shareAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAccount that = (ClientAccount) o;
        return clientName.equals(that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientName);
    }

    @Override
    public String toString() {

        return getClientName() + "\t" +
                getDollarAccount() + "\t" +
                shareAmount.get(StockNames.A) + "\t" +
                shareAmount.get(StockNames.B) + "\t" +
                shareAmount.get(StockNames.C) + "\t" +
                shareAmount.get(StockNames.D) + "\t";

    }
}
