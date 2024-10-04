CREATE SEQUENCE IF NOT EXISTS product_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS products (
    id INTEGER NOT NULL DEFAULT nextval('product_sequence') PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE SEQUENCE IF NOT EXISTS vendor_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS vendors (
    id INTEGER NOT NULL DEFAULT nextval('vendor_sequence') PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS vendor_product_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS vendor_products (
    id INTEGER NOT NULL DEFAULT nextval('vendor_product_sequence') PRIMARY KEY,
    vendor_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    CONSTRAINT fk_vendor FOREIGN KEY (vendor_id) REFERENCES vendors(id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);
