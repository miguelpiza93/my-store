CREATE SEQUENCE IF NOT EXISTS sales_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE sales (
    id INTEGER NOT NULL DEFAULT nextval('sales_sequence') PRIMARY KEY,
    status VARCHAR(100) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS sale_lines_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE sale_lines (
    id INTEGER NOT NULL DEFAULT nextval('sale_lines_sequence') PRIMARY KEY,
    sale_id INT NOT NULL,
    vendor_product_unit_variant_id INT NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    cost DECIMAL(10, 2),
    FOREIGN KEY (sale_id) REFERENCES sales(id),
    FOREIGN KEY (vendor_product_unit_variant_id) REFERENCES vendor_product_unit_variants(id)
);