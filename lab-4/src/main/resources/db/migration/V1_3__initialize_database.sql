CREATE TABLE IF NOT EXISTS cats.role
(
    id   bigserial NOT NULL PRIMARY KEY,
    name varchar   NOT NULL
);

INSERT INTO cats.role (id, name)
SELECT 1,
       'admin'
WHERE NOT EXISTS(SELECT id, name
                 FROM cats.role
                 WHERE id = 1
                   AND name = 'admin');

INSERT INTO cats.role (id, name)
SELECT 2,
       'user'
WHERE NOT EXISTS(SELECT id, name
                 FROM cats.role
                 WHERE id = 2
                   AND name = 'user');

CREATE TABLE IF NOT EXISTS cats.user
(
    id       bigserial NOT NULL PRIMARY KEY,
    username varchar   NOT NULL UNIQUE,
    password varchar   NOT NULL,
    role     bigint    NOT NULL,
    owner    bigint    NOT NULL UNIQUE,

    FOREIGN KEY (owner) REFERENCES cats.owner (id),
    FOREIGN KEY (role) REFERENCES cats.role (id)
);