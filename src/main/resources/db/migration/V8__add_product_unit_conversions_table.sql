CREATE SEQUENCE IF NOT EXISTS unit_conversion_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS unit_conversions (
    id INTEGER NOT NULL DEFAULT nextval('unit_conversion_sequence') PRIMARY KEY,
    product_id INTEGER,               -- Producto específico para la conversión; NULL para conversiones generales
    from_unit_id INTEGER NOT NULL,    -- Unidad de origen para la conversión (ej. caja, kilogramo)
    to_unit_id INTEGER NOT NULL,      -- Unidad de destino para la conversión (ej. unidad, gramo)
    conversion_factor DOUBLE PRECISION NOT NULL, -- Factor de conversión entre las unidades
    FOREIGN KEY (from_unit_id) REFERENCES units(id),
    FOREIGN KEY (to_unit_id) REFERENCES units(id),
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Inserts for product_unit_conversions table
INSERT INTO unit_conversions (product_id, from_unit_id, to_unit_id, conversion_factor)
VALUES
(1, 1, 2, 30.00),      -- 1 carton = 30 units
(1, 1, 3, 2.00),       -- 1 carton = 2 half cartons
(1, 3, 2, 15.00),      -- 1 half carton = 15 units
(2, 7, 2, 6.00),       -- 1 milk pack = 6 units
(NULL, 5, 4, 1000.00),    -- 1 kilogram = 1000 grams
(NULL, 6, 4, 500.00)      -- 1 pound = 500 grams
;