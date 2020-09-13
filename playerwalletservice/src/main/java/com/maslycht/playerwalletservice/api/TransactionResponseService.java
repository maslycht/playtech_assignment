package com.maslycht.playerwalletservice.api;

import com.maslycht.playerwalletservice.persistence.TransactionResponseRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionResponseService {
    private final LinkedHashMap<String, TransactionResponse> transactionResponseMap;

    public TransactionResponseService(TransactionResponseRepository transactionResponseRepository) {
        this.transactionResponseMap = new LinkedHashMap<>() {
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > 1000;
            }
        };
        transactionResponseRepository.getLastThousand().forEach(transactionResponse ->
                this.transactionResponseMap.put(transactionResponse.getTransactionID(), transactionResponse));
    }

    public List<TransactionResponse> getLastThousandTransactionResponses() {
        return new ArrayList<>(transactionResponseMap.values());
    }

    public Optional<TransactionResponse> getExistingTransactionResponse(String transactionID) {
        return Optional.ofNullable(transactionResponseMap.get(transactionID));
    }

    public void recordTransactionResponse(TransactionResponse transactionResponse) {
        transactionResponseMap.put(transactionResponse.getTransactionID(), transactionResponse);
    }
}
