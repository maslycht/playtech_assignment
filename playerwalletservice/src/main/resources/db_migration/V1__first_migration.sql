create table PLAYER
(
    USERNAME VARCHAR(100) not null
        primary key,
    BALANCE_VERSION INTEGER default 0 not null,
    BALANCE REAL default 0.0 not null
);

create table PLAYER_BLACKLIST
(
    USERNAME VARCHAR(100) not null
        constraint PLAYER_BLACKLIST_pk
            primary key
);

create unique index PLAYER_BLACKLIST_USERNAME_uindex
    on PLAYER_BLACKLIST (USERNAME);

create table TRANSACTION_RESPONSE
(
    TRANSACTION_ID VARCHAR(36) not null
        constraint TRANSACTION_RESPONSE_pk
            primary key,
    ERROR_CODE VARCHAR(100),
    BALANCE_VERSION INTEGER not null,
    BALANCE_CHANGE REAL not null,
    BALANCE_AFTER_CHANGE REAL not null,
    TIMESTAMP INTEGER default (strftime('%s','now')) not null
);

create index TRANSACTION_RESPONSE_TIMESTAMP_index
    on TRANSACTION_RESPONSE (TIMESTAMP);

create unique index TRANSACTION_RESPONSE_TRANSACTION_ID_uindex
    on TRANSACTION_RESPONSE (TRANSACTION_ID);

