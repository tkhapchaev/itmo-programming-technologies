CREATE SCHEMA IF NOT EXISTS cats;

CREATE TABLE IF NOT EXISTS cats.owner
(
    id        bigserial  NOT NULL PRIMARY KEY,
    name      varchar    NOT NULL,
    birthdate date       NOT NULL
);

CREATE TABLE IF NOT EXISTS cats.cat
(
    id        bigserial  NOT NULL PRIMARY KEY,
    name      varchar    NOT NULL,
    birthdate date       NOT NULL,
    breed     varchar,
    colour    varchar    NOT NULL,
    owner     bigint     NOT NULL,

    FOREIGN KEY (owner) REFERENCES cats.owner (id)
);