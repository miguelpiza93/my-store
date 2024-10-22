CREATE SEQUENCE IF NOT EXISTS unit_stock_items_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS vendor_product_unit_variants (
    id INTEGER NOT NULL DEFAULT nextval('unit_stock_items_sequence') PRIMARY KEY,
    vendor_product_id INTEGER NOT NULL,
    unit_id INTEGER NOT NULL,
    sale_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (vendor_product_id) REFERENCES vendor_products(id),
    FOREIGN KEY (unit_id) REFERENCES units(id)
);
