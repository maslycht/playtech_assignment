package com.maslycht.playerwalletservice.persistence;

import com.maslycht.playerwalletservice.api.TransactionResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionResponseRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SAVE_QUERY =
            "INSERT OR IGNORE INTO transaction_response (transaction_id, error_code, balance_version, balance_change, balance_after_change) " +
                    "VALUES (:transactionID, :errorCode, :balanceVersion, :balanceChange, :balanceAfterChange) ";
    private final static String SELECT_LAST_1000 =
            "SELECT transaction_id, error_code, balance_version, balance_change, balance_after_change " +
                    "FROM transaction_response ORDER BY timestamp DESC LIMIT 1000";

    public TransactionResponseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(List<TransactionResponse> transactionResponses) {
        for (TransactionResponse transactionResponse :
                transactionResponses) {
            this.save(transactionResponse);
        }
    }

    public void save(TransactionResponse transactionResponse) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("transactionID", transactionResponse.getTransactionID())
                .addValue("errorCode", transactionResponse.getErrorCode())
                .addValue("balanceVersion", transactionResponse.getBalanceVersion())
                .addValue("balanceChange", transactionResponse.getBalanceChange())
                .addValue("balanceAfterChange", transactionResponse.getBalanceAfterChange());
        jdbcTemplate.update(SAVE_QUERY, parameters);
    }

    public List<TransactionResponse> getLastThousand() {
        RowMapper<TransactionResponse> rowMapper = (resultSet, i) -> new TransactionResponse(
                resultSet.getString("transaction_id"),
                resultSet.getString("error_code"),
                resultSet.getInt("balance_version"),
                resultSet.getDouble("balance_change"),
                resultSet.getDouble("balance_after_change"));
        return jdbcTemplate.query(SELECT_LAST_1000, rowMapper);
    }
}
