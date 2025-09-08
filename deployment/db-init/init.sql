-- =========================================
-- TABLAS
-- =========================================

CREATE TABLE roles (
    role_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE,
    address VARCHAR(255),
    phone VARCHAR(50),
    identity_document VARCHAR(15),
    email VARCHAR(150) UNIQUE,
    password VARCHAR(150),
    base_salary NUMERIC(15, 2),
    role_id BIGINT NOT NULL
);

-- =========================================
-- DATOS DE PRUEBA
-- =========================================

INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator with full access'),
('ADVISOR', 'Advisor with specific permissions'),
('CLIENT', 'Client with limited access');

INSERT INTO users (first_name, last_name, birth_date, address, phone, identity_document, email, password, base_salary, role_id) VALUES
('Admin', 'Admin', '1985-04-12', 'Calle 123', '111', '111', 'admin@gmail.com', '$2a$10$hhAZonLDNvU5U1yV3d29I.Icb861KzUWRwrxmukpbjsOw0YyNATtK', 2500.00, 1),
('Advisor', 'Perez', '1985-04-12', 'Calle 123', '222', '222', 'advisor@gmail.com', '$2a$10$hhAZonLDNvU5U1yV3d29I.Icb861KzUWRwrxmukpbjsOw0YyNATtK', 2500.00, 2),
('Client', 'Gomez', '1990-09-25', 'Carrera 45', '333', '333', 'client@gmail.com', '$2a$10$hhAZonLDNvU5U1yV3d29I.Icb861KzUWRwrxmukpbjsOw0YyNATtK', 2000.00, 3);
