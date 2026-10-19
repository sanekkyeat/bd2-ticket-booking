DROP TABLE IF EXISTS tickets CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS ticket_categories CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS venues CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- 1. Tabela USERS (Roluri: CLIENT, ORGANIZER, ADMIN)
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CLIENT'
        CHECK (role IN ('CLIENT', 'ORGANIZER', 'ADMIN')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. Tabela VENUES (Locatii/Sali de concert)
CREATE TABLE venues (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    city VARCHAR(80) NOT NULL,
    address VARCHAR(255) NOT NULL,
    total_capacity INT NOT NULL CHECK (total_capacity > 0),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabela EVENTS (Concerte si evenimente muzicale)
CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    artist VARCHAR(120) NOT NULL,
    genre VARCHAR(50),
    event_date TIMESTAMPTZ NOT NULL,
    venue_id INT NOT NULL REFERENCES venues(id) ON DELETE RESTRICT,
    organizer_id INT NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED'
        CHECK (status IN ('SCHEDULED', 'CANCELLED', 'COMPLETED')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 4. Tabela TICKET_CATEGORIES (Categorii de locuri per concert: VIP, Fan Zone etc.)
CREATE TABLE ticket_categories (
    id SERIAL PRIMARY KEY,
    event_id INT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
    total_quantity INT NOT NULL CHECK (total_quantity > 0),
    available_quantity INT NOT NULL CHECK (available_quantity >= 0),
    CONSTRAINT chk_quantity CHECK (available_quantity <= total_quantity),
    CONSTRAINT uq_event_category UNIQUE (event_id, name)
);

-- 5. Tabela BOOKINGS (Rezervari/comenzi)
CREATE TABLE bookings (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    booking_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount NUMERIC(10, 2) NOT NULL CHECK (total_amount >= 0),
    status VARCHAR(20) NOT NULL DEFAULT 'CONFIRMED'
        CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED'))
);

-- 6. Tabela TICKETS (Bilete individuale generate)
CREATE TABLE tickets (
    id SERIAL PRIMARY KEY,
    ticket_code VARCHAR(36) NOT NULL UNIQUE,
    booking_id INT NOT NULL REFERENCES bookings(id) ON DELETE CASCADE,
    category_id INT NOT NULL REFERENCES ticket_categories(id) ON DELETE RESTRICT,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,
    issued_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);