CREATE SEQUENCE IF NOT EXISTS purchase_order_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS purchase_order (
    id INTEGER NOT NULL DEFAULT nextval('purchase_order_sequence') PRIMARY KEY,
    product_id INTEGER,
    vendor_id INTEGER,
    quantity INTEGER,
    unit_price DOUBLE PRECISION NOT NULL,
    created_at TIMESTAMP,
    estimated_delivery_date date,
    CONSTRAINT fk_vendor_po FOREIGN KEY (vendor_id) REFERENCES vendor(id),
    CONSTRAINT fk_product_po FOREIGN KEY (product_id) REFERENCES Product(id)
);
