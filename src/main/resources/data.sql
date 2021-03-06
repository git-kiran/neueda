DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT
(
    ACCOUNT_NUMBER VARCHAR(100) PRIMARY KEY NOT NULL,
    PIN            INT                      NOT NULL,
    OPEN_BALANCE   INT                      NOT NULL,
    OVERDRAFT      INT                      NOT NULL
);

INSERT INTO ACCOUNT (ACCOUNT_NUMBER, PIN, OPEN_BALANCE, OVERDRAFT)
VALUES (123456789, 1234, 800, 200),
       (987654321, 4321, 1230, 150);


