CREATE SEQUENCE IF NOT EXISTS stock_item_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS stock_items (
    id INTEGER NOT NULL DEFAULT nextval('stock_item_sequence') PRIMARY KEY,
    purchase_order_line_id INTEGER,
    quantity INTEGER,
    sale_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_purchase_order_line FOREIGN KEY (purchase_order_line_id) REFERENCES purchase_order_lines(id)
);
