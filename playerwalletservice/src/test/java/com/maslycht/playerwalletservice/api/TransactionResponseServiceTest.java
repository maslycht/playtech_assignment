package com.maslycht.playerwalletservice.api;

import com.maslycht.playerwalletservice.persistence.TransactionResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TransactionResponseServiceTest {

    TransactionResponseRepository transactionResponseRepository;

    TransactionResponseService transactionResponseService;

    @BeforeEach
    void setup() {
        transactionResponseRepository = mock(TransactionResponseRepository.class);
        transactionResponseService = new TransactionResponseService(transactionResponseRepository);
    }

    @Test
    void itShouldConsiderOnlyOneThousandLatestTransactions() {
        String transactionIDToBeOverwritten = UUID.randomUUID().toString();
        transactionResponseService.recordTransactionResponse(new TransactionResponse(
                transactionIDToBeOverwritten, null, 0, 0.0, 0.0));
        assertTrue(transactionResponseService.getExistingTransactionResponse(transactionIDToBeOverwritten).isPresent());
        for (int i = 0; i < 1000; i++) {
            transactionResponseService.recordTransactionResponse(new TransactionResponse(
                    UUID.randomUUID().toString(), null, 0, 0.0, 0.0));
        }
        assertTrue(transactionResponseService.getExistingTransactionResponse(transactionIDToBeOverwritten).isEmpty());
    }

    @Test
    void itShouldStoreAndReturnExistingTransactionResponse() {
        String transactionID = UUID.randomUUID().toString();
        transactionResponseService.recordTransactionResponse(new TransactionResponse(
                transactionID, null, 0, 0.0, 0.0));
        assertTrue(transactionResponseService.getExistingTransactionResponse(transactionID).isPresent());
    }

}