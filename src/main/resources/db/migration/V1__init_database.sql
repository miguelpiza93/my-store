CREATE SEQUENCE IF NOT EXISTS product_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS product (
    id INTEGER NOT NULL DEFAULT nextval('product_sequence') PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE SEQUENCE IF NOT EXISTS supplier_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS supplier (
    id INTEGER NOT NULL DEFAULT nextval('supplier_sequence') PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS supplier_product_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS supplier_product (
    id INTEGER NOT NULL DEFAULT nextval('supplier_product_sequence') PRIMARY KEY,
    supplier_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_supplier FOREIGN KEY (supplier_id) REFERENCES Supplier(id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Product(id) ON DELETE CASCADE
);
