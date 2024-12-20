INSERT INTO products(name, description, reference_unit)
values
('Salchicha', 'x6', 2)
;

INSERT INTO vendors(name)
values
('Kikes'),
('Alqueria'),
('Zenu')
;

INSERT INTO vendor_products(vendor_id, product_id, price)
values
(1, 1, 8500),-- Huevos Kike
(2, 2, 30000),-- Leche Alqueria
(3, 6, 2500) -- Salchicha Zenu
;

INSERT INTO vendor_product_unit_variants(vendor_product_id, unit_id, sale_price)
values
(1, 2, 500.0),-- Huevos Kike x unidad
(1, 1, 12000.0), -- Huevos kike x panal
(3, 2, 3000.0) -- Salchicha Zenu x unidad
;

INSERT INTO purchase_orders(status, created_at, estimated_delivery_date, vendor_id, total)
values
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1, 9000),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1, 46000),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1, 90000),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 1, 0),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 2, 0),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 3, 12500)
;

INSERT INTO purchase_order_lines(purchase_order_id, quantity, unit_price, total, vendor_product_id, created_at)
values
(1, 1, 9000, 9000, 1, DATEADD('DAY', -4, NOW())), -- Huevos Kikes ((9000*1) + (12000*1) + (8500*4) + (15000*2))/8
(2, 1, 12000, 12000, 1, DATEADD('DAY', -3, NOW())), -- Huevos Kikes
(2, 4, 8500, 34000, 1, DATEADD('DAY', -1, NOW())), -- Huevos Kikes
(3, 2, 15000, 30000, 1, DATEADD('DAY', -2, NOW())), -- Huevos Kikes
(3, 2, 30000, 60000, 2, DATEADD('DAY', -1, NOW())), -- Leche Alqueria
(6, 5, 2500, 12500, 3, DATEADD('DAY', -1, NOW())) -- Salchicha Zenu
;

INSERT INTO stock_items(purchase_order_line_id, quantity)
values
(1, 27),
(2, 30),
(3, 120),
(4, 60),
(5, 12),
(6, 5)
;

INSERT INTO sales(status, created_at)
values
('CLOSED', NOW())
;

INSERT INTO sale_lines(sale_id, vendor_product_unit_variant_id, quantity, unit_price, total, cost)
values
(1, 1, 3, 500, 1500, 900) -- Vendidas 3 unidades de huevos kike
;