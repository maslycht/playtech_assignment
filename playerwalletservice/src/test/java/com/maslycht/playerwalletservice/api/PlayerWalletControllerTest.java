package com.maslycht.playerwalletservice.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maslycht.playerwalletservice.model.PlayerBalanceChangeError;
import com.maslycht.playerwalletservice.model.PlayerBalanceChangeResult;
import com.maslycht.playerwalletservice.model.PlayerWalletService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static com.maslycht.playerwalletservice.model.PlayerBalanceChangeError.PLAYER_BLACKLISTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerWalletController.class)
class PlayerWalletControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    PlayerWalletService playerWalletService;

    @MockBean
    TransactionResponseService transactionResponseService;

    @Test
    void itShouldReturnExpectedResponse() throws Exception {
        String transactionID = UUID.randomUUID().toString();

        when(transactionResponseService.getExistingTransactionResponse(eq(transactionID))).thenReturn(Optional.empty());
        when(playerWalletService.processTransaction(anyString(), anyDouble())).thenReturn(
                new PlayerBalanceChangeResult(PLAYER_BLACKLISTED, 0, 0.0, 0.0));

        TransactionRequest request = new TransactionRequest("player1", transactionID, 10);
        TransactionResponse expectedResponse = new TransactionResponse(transactionID, PLAYER_BLACKLISTED.name(), 0, 0.0, 0.0);

        assertEquals(
                getObjectAsJson(expectedResponse),
                mvc.perform(MockMvcRequestBuilders
                        .post("/transaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectAsJson(request)))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    private String getObjectAsJson(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}