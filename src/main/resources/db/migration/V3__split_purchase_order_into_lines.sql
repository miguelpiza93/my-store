CREATE SEQUENCE IF NOT EXISTS purchase_order_line_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS purchase_order_line (
    id INTEGER NOT NULL DEFAULT nextval('purchase_order_line_sequence') PRIMARY KEY,
    purchase_order_id INTEGER,
    product_id INTEGER,
    quantity INTEGER,
    unit_price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Product(id),
    CONSTRAINT fk_purchase_order FOREIGN KEY (purchase_order_id) REFERENCES purchase_order(id)
);


ALTER TABLE purchase_order DROP CONSTRAINT fk_product;
ALTER TABLE purchase_order DROP COLUMN product_id;
ALTER TABLE purchase_order DROP COLUMN quantity;
ALTER TABLE purchase_order DROP COLUMN unit_price;
