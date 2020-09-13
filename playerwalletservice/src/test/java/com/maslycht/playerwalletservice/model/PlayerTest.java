package com.maslycht.playerwalletservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void itShouldNotSetBalanceBelowZero() {
        Player player = new Player("player1");
        boolean result = player.changeBalance(-5.0);
        assertFalse(result);
        assertEquals(0.0, player.getBalance());
    }

    @Test
    void itShouldIncreaseBalanceByGivenChangeAmount() {
        Player player = new Player("player1");
        double originalBalance = player.getBalance();
        double changeAmount = 50.0;
        boolean result = player.changeBalance(changeAmount);
        assertTrue(result);
        assertEquals(originalBalance + changeAmount, player.getBalance());
    }

    @Test
    void itShouldDecreaseBalanceByGivenChangeAmount() {
        Player player = new Player("player1", 0, 50.0);
        double originalBalance = player.getBalance();
        double changeAmount = -25.0;
        boolean result = player.changeBalance(changeAmount);
        assertTrue(result);
        assertEquals(originalBalance + changeAmount, player.getBalance());
    }
}