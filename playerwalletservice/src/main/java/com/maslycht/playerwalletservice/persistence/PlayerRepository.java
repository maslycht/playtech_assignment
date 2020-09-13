package com.maslycht.playerwalletservice.persistence;

import com.maslycht.playerwalletservice.model.Player;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static String SAVE_QUERY =
            "INSERT INTO player (username, balance_version, balance) VALUES (:username, :balanceVersion, :balance) " +
                    "ON CONFLICT(username) DO UPDATE SET balance_version = :balanceVersion, balance = :balance";
    private final static String SELECT_ALL_QUERY =
            "select * from PLAYER";


    public PlayerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAll(List<Player> players) {
        for (Player player :
                players) {
            this.save(player);
        }
    }

    public void save(Player player) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("username", player.getUsername())
                .addValue("balanceVersion", player.getBalanceVersion())
                .addValue("balance", player.getBalance());
        jdbcTemplate.update(SAVE_QUERY, parameters);
    }

    public List<Player> getAll() {
        RowMapper<Player> rowMapper = (resultSet, i) -> new Player(
                resultSet.getString("username"),
                resultSet.getInt("balance_version"),
                resultSet.getDouble("balance"));
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }
}
