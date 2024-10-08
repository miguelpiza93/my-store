INSERT INTO products(name, description, reference_unit, sale_price)
values
('Huevos', 'Huevos AA', 1, 0.0),
('Leche', 'Entera', 7, 0.0)
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
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 2)
;

INSERT INTO purchase_order_lines(quantity, unit_price, product_id, purchase_order_id)
values
(1, 9000, 1, 1),
(1, 12000, 1, 2),
(2, 15000, 1, 3),
(4, 8500, 1, 2),
(2, 30000, 2, 3)
;

INSERT INTO stock_items(purchase_order_line_id, quantity)
values
(1, 1),
(2, 1),
(3, 2)
;