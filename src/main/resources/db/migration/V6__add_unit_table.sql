CREATE SEQUENCE IF NOT EXISTS unit_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS units (
    id INTEGER NOT NULL DEFAULT nextval('unit_sequence') PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- E.g. egg, carton, gram, block
    symbol VARCHAR(10), -- Optional for unit symbols like "kg", "g", "u"
    is_fractional BOOLEAN DEFAULT FALSE, -- Defines if unit allows fractions (e.g. grams)
    is_base_unit BOOLEAN DEFAULT FALSE -- Defines if unit is the minimum and it does not have conversion
);

-- Alter product table to include reference_unit
ALTER TABLE products
ADD COLUMN reference_unit INTEGER REFERENCES units(id);

-- Inserts for units table
INSERT INTO units (name, symbol, is_fractional, is_base_unit)
VALUES
('Carton', 'CRT', FALSE, FALSE),
('Unit', 'U', FALSE, TRUE),
('Half Carton', 'HC', FALSE, FALSE),
('Gram', 'g', TRUE, TRUE),
('Kilogram', 'kg', TRUE, FALSE),
('Pound', 'lb', TRUE, FALSE),
('Milk Pack', 'MP', FALSE, FALSE)
;