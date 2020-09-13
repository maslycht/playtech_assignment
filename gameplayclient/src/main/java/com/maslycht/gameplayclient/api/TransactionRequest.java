package com.maslycht.gameplayclient.api;

public class TransactionRequest {
    private final String username;
    private final String transactionID;
    private final double balanceChange;

    public TransactionRequest(String username, String transactionID, double balanceChange) {
        this.username = username;
        this.transactionID = transactionID;
        this.balanceChange = balanceChange;
    }

    public String getUsername() {
        return username;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

}
