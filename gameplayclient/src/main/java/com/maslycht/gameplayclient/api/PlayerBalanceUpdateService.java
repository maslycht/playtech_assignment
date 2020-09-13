package com.maslycht.gameplayclient.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

@Service
public class PlayerBalanceUpdateService {
    private final static Logger logger = LoggerFactory.getLogger(PlayerBalanceUpdateService.class);
    private final RestTemplate restTemplate;
    private final String playerWalletServiceHostname;
    private final String playerWalletServicePort;

    public PlayerBalanceUpdateService(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${playerwalletservice.hostname}") String playerWalletServiceHostname,
            @Value("${playerwalletservice.port}") String playerWalletServicePort) {
        this.restTemplate = restTemplateBuilder.build();
        this.playerWalletServiceHostname = playerWalletServiceHostname;
        this.playerWalletServicePort = playerWalletServicePort;
    }

    public void postTransaction(String username, String transactionID, double balanceChange) {
        TransactionRequest request = new TransactionRequest(username, transactionID, balanceChange);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<TransactionRequest> entity = new HttpEntity<>(request, headers);

        logger.info("[REQUEST][{}][{}] - transaction for amount {}",
                request.getTransactionID(), request.getUsername(), request.getBalanceChange());

        TransactionResponse response = this.restTemplate.postForObject(
                getEndpointUrl("/transaction"), entity, TransactionResponse.class);

        logTransactionResponse(request, Objects.requireNonNull(response));
    }

    private String getEndpointUrl(String endpoint) {
        return "http://" + playerWalletServiceHostname + ":" + playerWalletServicePort + endpoint;
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
}
