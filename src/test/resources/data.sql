INSERT INTO products(name, description, reference_unit)
values
('Huevos', 'Huevos AA', 1),
('Leche', 'Entera', 7)
;

INSERT INTO vendors(name)
values
('Kikes'),
('Alqueria')
;

INSERT INTO vendor_products(vendor_id, product_id, price)
values
(1, 1, 8500),
(2, 2, 30000)
;

INSERT INTO purchase_orders(status, created_at, estimated_delivery_date, vendor_id)
values
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 2)
;

INSERT INTO purchase_order_lines(quantity, unit_price, product_id, purchase_order_id)
values
(2, 8400, 1, 1),
(4, 8500, 1, 2),
(2, 30000, 2, 3)
;

INSERT INTO stock_items(product_id, quantity, sale_price)
values
(1, 4, 300.0)
;