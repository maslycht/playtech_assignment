package com.maslycht.playerwalletservice.api;

public class TransactionResponse {
    private final String transactionID;
    private final String errorCode;
    private final int balanceVersion;
    private final double balanceChange;
    private final double balanceAfterChange;

    public TransactionResponse(String transactionID, String errorCode, int balanceVersion, double balanceChange, double balanceAfterChange) {
        this.transactionID = transactionID;
        this.errorCode = errorCode;
        this.balanceVersion = balanceVersion;
        this.balanceChange = balanceChange;
        this.balanceAfterChange = balanceAfterChange;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public int getBalanceVersion() {
        return balanceVersion;
    }

    public double getBalanceChange() {
        return balanceChange;
    }

    public double getBalanceAfterChange() {
        return balanceAfterChange;
    }
}
