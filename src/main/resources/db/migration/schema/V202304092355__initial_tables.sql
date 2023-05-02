CREATE SCHEMA IF NOT EXISTS dbuser;

SET search_path TO dbuser; --default location for creating new objects

CREATE TABLE IF NOT EXISTS customer_order (
    id uuid not null,
    order_id uuid not null,
    customer_id uuid not null,
    ordered_at timestamp with time zone not null,
    order_status varchar(255),
    processed_at timestamp with time zone not null,
    product_ids text [] not null,
    UNIQUE(order_id, customer_id)
);