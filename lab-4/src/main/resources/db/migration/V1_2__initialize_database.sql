ALTER TABLE IF EXISTS cats.cat
    ADD COLUMN IF NOT EXISTS tail_length int NOT NULL DEFAULT 0;

CREATE TABLE IF NOT EXISTS cats.flea
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar   NOT NULL,
    cat  bigint    NOT NULL,

    FOREIGN KEY (cat) REFERENCES cats.cat (id)
);