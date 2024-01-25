CREATE TABLE users
(
    id              BIGSERIAL PRIMARY KEY,
    username        VARCHAR(256)        NOT NULL,
    document_number VARCHAR(256) UNIQUE NOT NULL,
    document_type   VARCHAR(16)         NOT NULL
);

CREATE TABLE accounts
(
    id            BIGSERIAL PRIMARY KEY,
    balance       DECIMAL(1000, 2) NOT NULL,
    currency_type VARCHAR(4)       NOT NULL,
    user_id       BIGINT REFERENCES users (id)
);

CREATE TABLE transactions
(
    id               VARCHAR PRIMARY KEY,
    transaction_type VARCHAR(256)     NOT NULL,
    amount           DECIMAL(1000, 2) NOT NULL,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    account_id       BIGINT REFERENCES accounts (id)
);