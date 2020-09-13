package com.maslycht.playerwalletservice.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.maslycht.playerwalletservice.model.PlayerBalanceChangeError.*;

public class PlayerWalletService {
    private final Map<String, Player> playerMap;
    private final List<String> blacklistedPlayers;
    private final double balanceChangeLimit;

    public PlayerWalletService(
            Map<String, Player> playerMap, List<String> blacklistedPlayers, double balanceChangeLimit
    ) {
        this.playerMap = playerMap;
        this.blacklistedPlayers = blacklistedPlayers;
        this.balanceChangeLimit = balanceChangeLimit;
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(playerMap.values());
    }

    public PlayerBalanceChangeResult processTransaction(String username, double balanceChange) {
        Player player = playerMap.getOrDefault(username, new Player(username));

        if (isPlayerBlacklisted(username)) {
            return getResultWithError(PLAYER_BLACKLISTED, player);
        }

        if (Math.abs(balanceChange) > balanceChangeLimit) {
            return getResultWithError(BALANCE_CHANGE_LIMIT_EXCEEDED, player);
        }

        if (player.changeBalance(balanceChange)) {
            playerMap.put(player.getUsername(), player);
            return new PlayerBalanceChangeResult(player.getBalanceVersion(), balanceChange, player.getBalance());
        } else {
            return getResultWithError(BALANCE_LESS_THAN_ZERO, player);
        }
    }

    private boolean isPlayerBlacklisted(String username) {
        return blacklistedPlayers.contains(username);
    }

    private PlayerBalanceChangeResult getResultWithError(PlayerBalanceChangeError errorCode, Player player) {
        return new PlayerBalanceChangeResult(errorCode, player.getBalanceVersion(), 0, player.getBalance());
    }
}
