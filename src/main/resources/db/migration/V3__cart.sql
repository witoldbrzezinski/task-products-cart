CREATE TABLE carts (
    id bigserial PRIMARY KEY,
    total_price NUMERIC DEFAULT 0,
    is_deleted boolean NOT NULL DEFAULT false,
    uuid VARCHAR(36) NOT NULL,
    version BIGINT DEFAULT 0
 );

