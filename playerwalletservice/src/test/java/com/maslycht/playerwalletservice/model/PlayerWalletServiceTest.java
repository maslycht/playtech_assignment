package com.maslycht.playerwalletservice.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static com.maslycht.playerwalletservice.model.PlayerBalanceChangeError.BALANCE_CHANGE_LIMIT_EXCEEDED;
import static com.maslycht.playerwalletservice.model.PlayerBalanceChangeError.PLAYER_BLACKLISTED;
import static org.junit.jupiter.api.Assertions.*;

class PlayerWalletServiceTest {

    @Test
    public void itShouldNotAllowBlackListedPLayerTransaction() {
        Player player1 = new Player("player1");
        Map<String, Player> playerMap = new LinkedHashMap<>();
        playerMap.put(player1.getUsername(), player1);
        List<String> blacklistedPlayers = Collections.singletonList("player1");
        PlayerWalletService playerWalletService = new PlayerWalletService(playerMap, blacklistedPlayers, 100);

        for (Map.Entry<String, Player> player :
                playerMap.entrySet()) {
            assertEquals(PLAYER_BLACKLISTED, playerWalletService.processTransaction(player.getKey(), 10).getErrorCode());
        }
    }

    @Test
    public void itShouldNotExceedBalanceChangeLimit() {
        double balanceChangeLimit = 100.0;
        double balanceChangeAmount = (Math.random() * (Double.MAX_VALUE - balanceChangeLimit) + 1) + balanceChangeLimit;
        Player player1 = new Player("player1");
        Map<String, Player> playerMap = new LinkedHashMap<>();
        playerMap.put(player1.getUsername(), player1);
        PlayerWalletService playerWalletService = new PlayerWalletService(playerMap, new ArrayList<>(), balanceChangeLimit);

        assertTrue(balanceChangeAmount > balanceChangeLimit);
        for (Map.Entry<String, Player> player :
                playerMap.entrySet()) {
            assertEquals(BALANCE_CHANGE_LIMIT_EXCEEDED, playerWalletService.processTransaction(player.getKey(), balanceChangeAmount).getErrorCode());
        }
    }

    @Test
    public void itShouldCreateANewUserOnlyOnSuccessfulBalanceChangeIfNotExists() {
        String newPlayerUsername1 = "player1";
        String newPlayerUsername2 = "player2";
        PlayerWalletService playerWalletService = new PlayerWalletService(new LinkedHashMap<>(), new ArrayList<>(), 100);
        playerWalletService.processTransaction(newPlayerUsername1, 0);
        playerWalletService.processTransaction(newPlayerUsername2, -5);
        assertTrue(playerWalletService.getAllPlayers().stream().anyMatch(player -> player.getUsername().equals(newPlayerUsername1)));
        assertFalse(playerWalletService.getAllPlayers().stream().anyMatch(player -> player.getUsername().equals(newPlayerUsername2)));
    }

    @Test
    public void itShouldReturnAllPlayersStoredInMemory() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Map<String, Player> playerMap = new LinkedHashMap<>();
        playerMap.put(player1.getUsername(), player1);
        playerMap.put(player2.getUsername(), player2);
        PlayerWalletService playerWalletService = new PlayerWalletService(playerMap, new ArrayList<>(), 100);

        for (Player player :
                playerMap.values()) {
            assertTrue(playerWalletService.getAllPlayers().contains(player));
        }
    }
}