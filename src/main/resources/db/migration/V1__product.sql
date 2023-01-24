CREATE TABLE products (
    id bigserial PRIMARY KEY,
    title VARCHAR (255) NOT NULL,
    price MONEY,
    quantity INTEGER,
    is_deleted boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0
 );

