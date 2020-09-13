package com.maslycht.gameplayclient.api;

public class TransactionResponse {
    private String transactionID;
    private String errorCode;
    private int balanceVersion;
    private double balanceChange;
    private double balanceAfterChange;

    // required for Jackson serialization
    public TransactionResponse() {
    }

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

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", balanceVersion=" + balanceVersion +
                ", balanceChange=" + balanceChange +
                ", balanceAfterChange=" + balanceAfterChange +
                '}';
    }
}
