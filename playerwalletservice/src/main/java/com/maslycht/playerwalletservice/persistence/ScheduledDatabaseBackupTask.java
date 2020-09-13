package com.maslycht.playerwalletservice.persistence;

import com.maslycht.playerwalletservice.api.TransactionResponse;
import com.maslycht.playerwalletservice.api.TransactionResponseService;
import com.maslycht.playerwalletservice.model.Player;
import com.maslycht.playerwalletservice.model.PlayerWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledDatabaseBackupTask {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledDatabaseBackupTask.class);
    private static final long DATABASE_BACKUP_TASK_RUN_RATE = 10000;

    private final TransactionResponseRepository transactionResponseRepository;
    private final TransactionResponseService transactionResponseService;
    private final PlayerRepository playerRepository;
    private final PlayerWalletService playerWalletService;

    public ScheduledDatabaseBackupTask(
            TransactionResponseRepository transactionResponseRepository,
            PlayerRepository playerRepository,
            TransactionResponseService transactionResponseService, PlayerWalletService playerWalletService
    ) {
        this.transactionResponseRepository = transactionResponseRepository;
        this.playerRepository = playerRepository;
        this.transactionResponseService = transactionResponseService;
        this.playerWalletService = playerWalletService;
    }

    @Scheduled(fixedRate = DATABASE_BACKUP_TASK_RUN_RATE)
    public void backupToDatabase() {
        logger.info("Backing up the database...");
        backupPlayers();
        backupTransactionResponses();
        logger.info("Database backup complete");
    }

    private void backupPlayers() {
        logger.info("Backing up players...");
        List<Player> players = playerWalletService.getAllPlayers();
        playerRepository.saveAll(players);
        logger.info("Players backup complete");
    }

    private void backupTransactionResponses() {
        logger.info("Backing up transaction responses...");
        List<TransactionResponse> transactionResponses = transactionResponseService.getLastThousandTransactionResponses();
        transactionResponseRepository.saveAll(transactionResponses);
        logger.info("Transaction responses backup complete");
    }
}
