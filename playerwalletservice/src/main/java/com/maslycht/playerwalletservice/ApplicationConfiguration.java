package com.maslycht.playerwalletservice;

import com.maslycht.playerwalletservice.model.Player;
import com.maslycht.playerwalletservice.persistence.PlayerBlacklistRepository;
import com.maslycht.playerwalletservice.persistence.PlayerRepository;
import com.maslycht.playerwalletservice.model.PlayerWalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {
    private final PlayerRepository playerRepository;
    private final PlayerBlacklistRepository playerBlacklistRepository;
    private final double balanceChangeLimit;

    @Bean
    public PlayerWalletService playerWalletService() {
        return new PlayerWalletService(
                getPlayerMap(playerRepository.getAll()),
                playerBlacklistRepository.getAll(),
                balanceChangeLimit
        );
    }

    public ApplicationConfiguration(
            PlayerRepository playerRepository,
            PlayerBlacklistRepository playerBlacklistRepository,
            @Value("${balanceChangeLimit:1000}") double balanceChangeLimit
    ) {
        this.playerRepository = playerRepository;
        this.playerBlacklistRepository = playerBlacklistRepository;
        this.balanceChangeLimit = balanceChangeLimit;
    }

    private Map<String, Player> getPlayerMap(List<Player> players) {
        return players.stream().collect(Collectors.toMap(Player::getUsername, player -> player));
    }
}
