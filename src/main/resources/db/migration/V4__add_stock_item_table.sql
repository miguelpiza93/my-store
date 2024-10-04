CREATE SEQUENCE IF NOT EXISTS stock_item_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS stock_items (
    id INTEGER NOT NULL DEFAULT nextval('stock_item_sequence') PRIMARY KEY,
    product_id INTEGER,
    quantity INTEGER,
    sale_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_product_stock_item FOREIGN KEY (product_id) REFERENCES Products(id)
);
