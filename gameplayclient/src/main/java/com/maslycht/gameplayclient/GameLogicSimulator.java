package com.maslycht.gameplayclient;

import com.maslycht.gameplayclient.api.PlayerBalanceUpdateService;
import com.maslycht.gameplayclient.api.TransactionRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class GameLogicSimulator {
    private static final List<String> USERNAMES;
    private static final long REQUEST_GENERATION_RATE = 1000;
    private static final Random random = new Random();
    private final PlayerBalanceUpdateService playerBalanceUpdateService;

    static {
        USERNAMES = Arrays.asList(
                "Bowmesmasollaju",
                "soonr4",
                "usaaf96",
                "oxibiobopsinepk",
                "vir4038o0",
                "Ribolanf",
                "Drusereebarma2d",
                "briadinovi55",
                "bainruamf",
                "Monnisvo"
        );
    }

    public GameLogicSimulator(PlayerBalanceUpdateService playerBalanceUpdateService) {
        this.playerBalanceUpdateService = playerBalanceUpdateService;
    }

    @Scheduled(fixedRate = REQUEST_GENERATION_RATE)
    public void performTransaction() {
        playerBalanceUpdateService.postTransaction(
                getRandomUsernameFromList(),
                UUID.randomUUID().toString(),
                getRandomBalanceChangeValue());
    }

    private String getRandomUsernameFromList() {
        return USERNAMES.get(random.nextInt(USERNAMES.size()));
    }

    private double getRandomBalanceChangeValue() {
        boolean isPositive = random.nextBoolean();
        double balanceChangeValue = random.nextInt(15000) / 100.0;
        return isPositive ? balanceChangeValue : balanceChangeValue * -1;
    }
}
