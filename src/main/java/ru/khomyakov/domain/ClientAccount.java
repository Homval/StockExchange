package ru.khomyakov.domain;

public class ClientAccount {
    private final String clientName;
    private int dollarAccount;
    private int shareA;
    private int shareB;
    private int shareC;
    private int shareD;

    public ClientAccount(String clientName, int dollarAccount, int shareA, int shareB, int shareC, int shareD) {
        this.clientName = clientName;
        this.dollarAccount = dollarAccount;
        this.shareA = shareA;
        this.shareB = shareB;
        this.shareC = shareC;
        this.shareD = shareD;
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

    public int getShareA() {
        return shareA;
    }

    public void setShareA(int shareA) {
        this.shareA = shareA;
    }

    public int getShareB() {
        return shareB;
    }

    public void setShareB(int shareB) {
        this.shareB = shareB;
    }

    public int getShareC() {
        return shareC;
    }

    public void setShareC(int shareC) {
        this.shareC = shareC;
    }

    public int getShareD() {
        return shareD;
    }

    public void setShareD(int shareD) {
        this.shareD = shareD;
    }
}
