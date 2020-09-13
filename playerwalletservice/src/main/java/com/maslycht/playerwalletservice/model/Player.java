package com.maslycht.playerwalletservice.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Player {
    private final String username;
    private int balanceVersion;
    private double balance;

    public Player(String username) {
        this.username = username;
    }

    public Player(String username, int balanceVersion, double balance) {
        this.username = username;
        this.balanceVersion = balanceVersion;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public int getBalanceVersion() {
        return balanceVersion;
    }

    public double getBalance() {
        return balance;
    }

    public boolean changeBalance(double balanceChange) {
        BigDecimal newBalance = BigDecimal.valueOf(this.balance)
                .add(BigDecimal.valueOf(balanceChange))
                .setScale(2, RoundingMode.HALF_UP);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        this.balance = newBalance.doubleValue();
        this.balanceVersion++;
        return true;
    }
}
