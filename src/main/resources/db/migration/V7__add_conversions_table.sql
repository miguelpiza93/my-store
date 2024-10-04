CREATE SEQUENCE IF NOT EXISTS conversion_sequence
INCREMENT BY 1
START WITH 1
;

CREATE TABLE IF NOT EXISTS conversions (
    id INTEGER NOT NULL DEFAULT nextval('unit_sequence') PRIMARY KEY,
    from_unit_id INTEGER, -- Unit from which the conversion happens (e.g. carton)
    to_unit_id INTEGER, -- Unit to which the conversion happens (e.g. unit)
    conversion_factor DECIMAL(10, 5) NOT NULL, -- E.g. 1 carton = 30 units
    FOREIGN KEY (from_unit_id) REFERENCES units(id),
    FOREIGN KEY (to_unit_id) REFERENCES units(id)
);

-- Inserts for conversions table
INSERT INTO conversions (from_unit_id, to_unit_id, conversion_factor)
VALUES
(1, 2, 30.00),      -- 1 carton = 30 units
(2, 1, 23.00),      -- 1 carton = 30 units
(3, 2, 15.00),      -- 1 half carton = 15 units
(7, 2, 6.00),       -- 1 milk pack = 6 units
(5, 4, 1000.00),    -- 1 kilogram = 1000 grams
(6, 4, 500.00)      -- 1 pound = 500 grams
;