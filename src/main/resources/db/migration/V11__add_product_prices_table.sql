CREATE SEQUENCE IF NOT EXISTS product_prices_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS product_prices (
    id INTEGER NOT NULL DEFAULT nextval('product_prices_sequence') PRIMARY KEY,
    product_id INTEGER NOT NULL,
    unit_id INTEGER NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (unit_id) REFERENCES units(id)
);
