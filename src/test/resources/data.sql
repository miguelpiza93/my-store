INSERT INTO products(name, description, reference_unit)
values
('Huevos', 'AA', 1),
('Leche', 'Entera', 7),
('Salchicha', 'x6', 2)
;

INSERT INTO product_prices(product_id, unit_id, sale_price)
values
(1, 2, 500.0),
(1, 1, 12000.0),
(3, 2, 3000.0)
;

INSERT INTO vendors(name)
values
('Kikes'),
('Alqueria'),
('Zenu')
;

INSERT INTO vendor_products(vendor_id, product_id, price)
values
(1, 1, 8500),
(2, 2, 30000),
(3, 3, 2500)
;

INSERT INTO purchase_orders(status, created_at, estimated_delivery_date, vendor_id)
values
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 1),
('PENDING', NOW(), DATEADD('DAY', 2, NOW()), 2),
('RECEIVED', NOW(), DATEADD('DAY', 2, NOW()), 3)
;

INSERT INTO purchase_order_lines(quantity, unit_price, product_id, purchase_order_id, created_at)
values
(1, 9000, 1, 1, DATEADD('DAY', -4, NOW())),
(1, 12000, 1, 2, DATEADD('DAY', -3, NOW())),
(2, 15000, 1, 3, DATEADD('DAY', -2, NOW())),
(4, 8500, 1, 2, DATEADD('DAY', -1, NOW())),
(2, 30000, 2, 3, DATEADD('DAY', -1, NOW())),
(5, 2500, 3, 6, DATEADD('DAY', -1, NOW()))
;

INSERT INTO stock_items(purchase_order_line_id, quantity)
values
(1, 30),
(2, 30),
(3, 30),
(6, 5)
;