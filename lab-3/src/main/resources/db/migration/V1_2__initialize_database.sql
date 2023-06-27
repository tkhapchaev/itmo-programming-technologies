ALTER TABLE cats.cat
    ADD tail_length int NOT NULL default 0;

CREATE TABLE IF NOT EXISTS cats.flea
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar   NOT NULL,
    cat  bigint    NOT NULL,

    FOREIGN KEY (cat) REFERENCES cats.cat (id)
);