package com.maslycht.playerwalletservice.model;

public class PlayerBalanceChangeResult {
    private PlayerBalanceChangeError errorCode;
    private final int balanceVersion;
    private final double balanceChange;
    private final double balanceAfterChange;

    public PlayerBalanceChangeResult(int balanceVersion, double balanceChange, double balanceAfterChange) {
        this.balanceVersion = balanceVersion;
        this.balanceChange = balanceChange;
        this.balanceAfterChange = balanceAfterChange;
    }

    public PlayerBalanceChangeResult(PlayerBalanceChangeError errorCode, int balanceVersion, double balanceChange,
                                     double balanceAfterChange) {
        this.errorCode = errorCode;
        this.balanceVersion = balanceVersion;
        this.balanceChange = balanceChange;
        this.balanceAfterChange = balanceAfterChange;
    }

    public PlayerBalanceChangeError getErrorCode() {
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
