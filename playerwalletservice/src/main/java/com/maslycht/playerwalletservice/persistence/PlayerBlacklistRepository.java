package com.maslycht.playerwalletservice.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerBlacklistRepository {
    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_ALL_QUERY = "select * from PLAYER_BLACKLIST";

    public PlayerBlacklistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAll() {
        RowMapper<String> rowMapper = ((resultSet, i) -> resultSet.getString("USERNAME"));
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }
}
