package com.maslycht.playerwalletservice.model;

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
        // FIXME: decimal precision
        double newBalance = this.balance + balanceChange;
        if (newBalance < 0) {
            return false;
        }

        this.balance = newBalance;
        this.balanceVersion++;
        return true;
    }
}
