
INSERT INTO roles (name) VALUES 
('ADMIN'),
('SUPPLIER'),
('CLIENTE');

INSERT INTO users (active, role_id, address, email, name, password, phone) VALUES 
-- Administradores (2 adicionales)
(true, 1, 'Av. Sistemas 303', 'admin2@toolrental.com', 'Ana Administradora', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-100-0003'),
(true, 1, 'Blvd. Tecnología 404', 'admin3@toolrental.com', 'Luis Sistemas', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-100-0004'),

-- Proveedores (5 adicionales)
(true, 2, 'Calle Maquinaria 505', 'proveedor3@maquinaria.com', 'Roberto Maquinaria', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-200-0003'),
(true, 2, 'Av. Equipos 606', 'proveedor4@equipos.com', 'Sofía Equipos', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-200-0004'),
(true, 2, 'Blvd. Herramientas 707', 'proveedor5@htools.com', 'Pedro Herramientas', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-200-0005'),
(true, 2, 'Calle Suministros 808', 'proveedor6@sumins.com', 'Lucía Suministros', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-200-0006'),
(true, 2, 'Av. Industrial 909', 'proveedor7@ind.com', 'Fernando Industrial', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-200-0007'),

-- Clientes (8 adicionales)
(true, 3, 'Calle Obra 1010', 'cliente3@constru.com', 'Constructora Díaz', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0003'),
(true, 3, 'Av. Proyectos 1111', 'cliente4@proyectos.com', 'Ingeniería Martínez', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0004'),
(true, 3, 'Blvd. Edificación 1212', 'cliente5@edifica.com', 'Arquitectura López', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0005'),
(true, 3, 'Calle Construcción 1313', 'cliente6@build.com', 'Obras Sánchez', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0006'),
(true, 3, 'Av. Ingenieros 1414', 'cliente7@engineer.com', 'Diseños Pérez', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0007'),
(true, 3, 'Blvd. Civil 1515', 'cliente8@civil.com', 'Ingeniería Civil SRL', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0008'),
(true, 3, 'Calle Arquitectos 1616', 'cliente9@arch.com', 'Estudio Ramírez', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0009'),
(true, 3, 'Av. Diseño 1717', 'cliente10@design.com', 'Diseño y Construcción', '$2a$10$xJwL5v5Jz5U6Z5b5z5b5Z.', '555-300-0010');


INSERT INTO clients (user_id, business_name, tax_id) VALUES 
(8, 'Constructores Unidos SA de CV', 'CUN123456789'),
(9, 'Ingeniería y Diseño SC', 'IND987654321'),
(10, 'Obras Civiles Modernas', 'OCM456789123'),
(11, 'Arquitectura Avanzada', 'ARQ789123456'),
(12, 'Proyectos Integrales', 'PRO321654987');

INSERT INTO suppliers (user_id, business_name, company_name, tax_id) VALUES 
(3, 'Herramientas Profesionales', 'HP Tools SA de CV', 'HPT123456789'),
(4, 'Suministros Industriales', 'SI Corporativo', 'SIC987654321'),
(5, 'Equipos de Construcción', 'ECON Global', 'ECG456789123'),
(6, 'Maquinaria Pesada', 'MAPESA', 'MAP789123456'),
(7, 'Ferretería Industrial', 'FERRIN SA', 'FER321654987');

INSERT INTO categories (description, name) VALUES 
('Herramientas eléctricas para construcción', 'Herramientas Eléctricas'),
('Equipo para medición precisa', 'Instrumentos de Medición'),
('Herramientas manuales tradicionales', 'Herramientas Manuales'),
('Equipo de seguridad para obra', 'Equipo de Seguridad'),
('Maquinaria pesada para construcción', 'Maquinaria Pesada');

-- Corrección para la tabla tools (IDs correctos basados en las inserciones previas)
INSERT INTO tools (active, available_quantity, cost_per_day, category_id, supplier_id, brand, description, model, name) VALUES 
(true, 5, 250.00, 1, 3, 'DEWALT', 'Taladro percutor 1/2" 20V', 'DCD996P2', 'Taladro Percutor'),
(true, 3, 500.00, 1, 3, 'BOSCH', 'Martillo demoledor 30kg', 'GBH 8-45 DV', 'Martillo Demoledor'),
(true, 10, 150.00, 3, 4, 'STANLEY', 'Juego de llaves combinadas 10 piezas', '92-809', 'Juego de Llaves'),
(true, 8, 300.00, 4, 4, '3M', 'Casco de seguridad con visor', 'H-700', 'Casco de Seguridad'),
(true, 2, 2500.00, 5, 5, 'CATERPILLAR', 'Compactador de suelo vibratorio', 'CS54B', 'Rodillo Compactador');

INSERT INTO reservations (end_date, start_date, total_cost, client_id, reservation_date, supplier_id, tool_id, status) VALUES 
('2023-06-15', '2023-06-10', 1250.00, 4, '2023-05-20 10:00:00', 2, 1, 'COMPLETADA'),
('2023-07-20', '2023-07-15', 2500.00, 5, '2023-06-01 14:30:00', 2, 2, 'PENDIENTE'),
('2023-08-12', '2023-08-05', 1050.00, 6, '2023-07-10 09:15:00', 3, 3, 'CONFIRMADA'),
('2023-09-01', '2023-08-25', 2400.00, 7, '2023-07-15 11:45:00', 3, 4, 'CANCELADA'),
('2023-10-10', '2023-10-01', 22500.00, 8, '2023-08-20 16:20:00', 4, 5, 'PROGRAMADA');

INSERT INTO notifications (read, creation_date, user_id, message, title) VALUES 
(false, '2023-05-20 10:05:00', 4, 'Su reserva #1 ha sido recibida', 'Reserva Recibida'),
(true, '2023-06-01 14:35:00', 5, 'Su reserva #2 está pendiente de confirmación', 'Reserva Pendiente'),
(false, '2023-07-10 09:20:00', 6, 'Su reserva #3 ha sido confirmada', 'Reserva Confirmada'),
(true, '2023-07-15 11:50:00', 7, 'Su reserva #4 ha sido cancelada', 'Reserva Cancelada'),
(false, '2023-08-20 16:25:00', 8, 'Su reserva #5 está programada', 'Reserva Programada');

INSERT INTO damages (repair_cost, report_date, reservation_id, tool_id, description, status) VALUES 
(350.00, '2023-06-16', 1, 1, 'Cable dañado por mal uso', 'REPARADO'),
(0.00, '2023-07-21', 2, 2, 'Herramienta en perfecto estado', 'SIN DAÑOS'),
(150.00, '2023-08-13', 3, 3, 'Llave de 10mm perdida', 'PENDIENTE'),
(0.00, '2023-09-02', 4, 4, 'Equipo sin daños', 'SIN DAÑOS'),
(1200.00, '2023-10-11', 5, 5, 'Fuga de aceite en motor', 'EN REPARACIÓN');

INSERT INTO invoices (issue_date, subtotal, tax, total, reservation_id, folio, issuer_tax_id, pdf, receiver_tax_id, xml) VALUES 
('2023-06-16', 1250.00, 200.00, 1450.00, 1, 'FAC-001', 'HPT123456789', 'factura1.pdf', 'CUN123456789', 'factura1.xml'),
('2023-07-21', 2500.00, 400.00, 2900.00, 2, 'FAC-002', 'HPT123456789', 'factura2.pdf', 'IND987654321', 'factura2.xml'),
('2023-08-13', 1050.00, 168.00, 1218.00, 3, 'FAC-003', 'SIC987654321', 'factura3.pdf', 'OCM456789123', 'factura3.xml'),
('2023-09-02', 2400.00, 384.00, 2784.00, 4, 'FAC-004', 'SIC987654321', 'factura4.pdf', 'ARQ789123456', 'factura4.xml'),
('2023-10-11', 22500.00, 3600.00, 26100.00, 5, 'FAC-005', 'ECG456789123', 'factura5.pdf', 'PRO321654987', 'factura5.xml');

INSERT INTO payments (amount, payment_date, reservation_id, payment_method, status, transaction_id) VALUES 
(1450.00, '2023-06-16 12:00:00', 1, 'TARJETA_CREDITO', 'COMPLETADO', 'TXN123456789'),
(2900.00, '2023-07-21 09:30:00', 2, 'TRANSFERENCIA', 'COMPLETADO', 'TXN987654321'),
(1218.00, '2023-08-13 14:15:00', 3, 'EFECTIVO', 'COMPLETADO', 'TXN456789123'),
(0.00, NULL, 4, NULL, 'CANCELADO', NULL),
(26100.00, '2023-10-11 10:45:00', 5, 'TARJETA_DEBITO', 'PENDIENTE', 'TXN321654987');