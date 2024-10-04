CREATE SEQUENCE IF NOT EXISTS purchase_order_line_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS purchase_order_lines (
    id INTEGER NOT NULL DEFAULT nextval('purchase_order_line_sequence') PRIMARY KEY,
    purchase_order_id INTEGER,
    product_id INTEGER,
    quantity INTEGER,
    unit_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_product_3 FOREIGN KEY (product_id) REFERENCES Products(id),
    CONSTRAINT fk_purchase_order FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id)
);


ALTER TABLE purchase_orders DROP CONSTRAINT fk_product_po;
ALTER TABLE purchase_orders DROP COLUMN product_id;
ALTER TABLE purchase_orders DROP COLUMN quantity;
ALTER TABLE purchase_orders DROP COLUMN unit_price;
