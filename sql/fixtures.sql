-- 1. Fixtures pentru utilizatori (parole mock cu hash BCrypt)
INSERT INTO users (full_name, email, password_hash, phone, role) VALUES
('Ion Popescu', 'client@example.com', '$2a$10$wE9...mockHashClient', '+40711111111', 'CLIENT'),
('Maria Ionescu', 'client2@example.com', '$2a$10$wE9...mockHashClient2', '+40722222222', 'CLIENT'),
('Live Events SRL', 'organizer@example.com', '$2a$10$wE9...mockHashOrg', '+40733333333', 'ORGANIZER'),
('Admin Central', 'admin@example.com', '$2a$10$wE9...mockHashAdmin', '+40744444444', 'ADMIN');

-- 2. Fixtures pentru locații (Venues)
-- Adăugat: Arena Națională din București (capacitate mare pentru stadioane)
INSERT INTO venues (name, city, address, total_capacity) VALUES
('Arena Națională', 'București', 'Bulevardul Basarabia 37-39', 55600),
('BTarena', 'Cluj-Napoca', 'Str. Uzinei Electrice', 10000),
('Teatrul Național de Operă', 'Galați', 'Str. Domnească 115', 800);

-- 3. Fixtures pentru evenimente
INSERT INTO events (title, artist, genre, event_date, venue_id, organizer_id, status) VALUES
('The Future Past Tour', 'Iron Maiden', 'Heavy Metal', '2027-06-20 19:30:00+03', 1, 3, 'SCHEDULED'),
('Symphonic Evening', 'Orchestra Națională', 'Clasic', '2026-12-01 18:00:00+02', 3, 3, 'SCHEDULED');

-- 4. Fixtures pentru categorii de bilete (adaptate pentru concertul Iron Maiden pe stadion)
INSERT INTO ticket_categories (event_id, name, price, total_quantity, available_quantity) VALUES
(1, 'Golden Circle', 450.00, 5000, 4998),
(1, 'General Access (Gazon)', 220.00, 20000, 20000),
(1, 'VIP Tribuna', 650.00, 1500, 1499),
(2, 'Categoria 1', 150.00, 200, 200);

-- 5. Fixtures pentru comenzi existente
INSERT INTO bookings (user_id, booking_date, total_amount, status) VALUES
(1, '2026-10-18 14:20:00+03', 1100.00, 'CONFIRMED');

-- 6. Fixtures pentru bilete emise în cadrul comenzii
INSERT INTO tickets (ticket_code, booking_id, category_id, is_used) VALUES
('b19d44e5-8f6a-4b96-9812-7935f1a92e11', 1, 1, FALSE),
('c88a10f2-4911-4fa3-94c3-2bfa8c719d20', 1, 3, FALSE);