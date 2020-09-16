package com.maslycht.playerwalletservice.api;

import com.maslycht.playerwalletservice.model.PlayerBalanceChangeResult;
import com.maslycht.playerwalletservice.model.PlayerWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
public class PlayerWalletController {
    private final Logger logger = LoggerFactory.getLogger(PlayerWalletController.class);
    private final PlayerWalletService playerWalletService;
    private final TransactionResponseService transactionResponseService;

    public PlayerWalletController(
            PlayerWalletService playerWalletService,
            TransactionResponseService transactionResponseService
    ) {
        this.playerWalletService = playerWalletService;
        this.transactionResponseService = transactionResponseService;
    }

    @PostMapping("/transaction")
    public TransactionResponse postTransaction(@RequestBody TransactionRequest request) {
        logger.info("[REQUEST][{}][{}] - transaction for amount {}",
                request.getTransactionID(), request.getUsername(), request.getBalanceChange());
        TransactionResponse response = transactionResponseService.getExistingTransactionResponse(request
                .getTransactionID()).orElseGet(() -> getTransactionResponse(request));
        logTransactionResponse(request, response);
        return response;
    }

    private void logTransactionResponse(TransactionRequest request, TransactionResponse response) {
        if (response.getErrorCode() != null) {
            logger.info("[RESPONSE][{}][{}] - transaction processing error: {}",
                    response.getTransactionID(), request.getUsername(), response.getErrorCode());
        } else {

            logger.info("[RESPONSE][{}][{}] - transaction processed successfully. User balance changed to {}",
                    response.getTransactionID(), request.getUsername(), response.getBalanceAfterChange());
        }
    }

    private TransactionResponse getTransactionResponse(TransactionRequest request) {
        PlayerBalanceChangeResult playerBalanceChangeResult = playerWalletService
                .processTransaction(request.getUsername(), request.getBalanceChange());
        TransactionResponse response = new TransactionResponse(
                request.getTransactionID(),
                playerBalanceChangeResult.getErrorCode() != null ? playerBalanceChangeResult.getErrorCode().name() : null,
                playerBalanceChangeResult.getBalanceVersion(),
                playerBalanceChangeResult.getBalanceChange(),
                playerBalanceChangeResult.getBalanceAfterChange()
        );
        transactionResponseService.recordTransactionResponse(response);
        return response;
    }
}
