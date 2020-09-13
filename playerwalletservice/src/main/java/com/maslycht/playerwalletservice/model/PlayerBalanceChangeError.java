package com.maslycht.playerwalletservice.model;

public enum PlayerBalanceChangeError {
    BALANCE_LESS_THAN_ZERO,
    BALANCE_CHANGE_LIMIT_EXCEEDED,
    PLAYER_BLACKLISTED
}
