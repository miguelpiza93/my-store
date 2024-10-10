CREATE SEQUENCE IF NOT EXISTS sales_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE sales (
    id INTEGER NOT NULL DEFAULT nextval('sales_sequence') PRIMARY KEY,
    product_id INT NOT NULL,
    quantity DECIMAL(10, 2) NOT NULL,
    unit_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    cost DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (unit_id) REFERENCES units(id)
);