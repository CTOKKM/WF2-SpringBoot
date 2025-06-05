-- Insert roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

-- Insert admin user (password: admin123)
INSERT INTO users (email, password, name) 
VALUES ('admin@example.com', '$2a$10$rDkPvvAFV6GgJjXpYWYw8OZJzqKxX5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5Z5', 'Admin User');

-- Assign admin role to admin user
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@example.com' AND r.name = 'ROLE_ADMIN';

-- Insert sample products
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S6', 'Samsung Corp', 'Korea', 600);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S8', 'Samsung Corp', 'Korea', 800);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S10', 'Samsung Corp', 'Korea', 1000);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S21', 'Samsung Corp', 'Korea', 1000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Air1', 'Apple', 'China', 10000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Air2', 'Apple', 'China', 10000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Air3', 'Apple', 'China', 10000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Air4', 'Apple', 'China', 10000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Air5', 'Apple', 'China', 10000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Pro1', 'Apple', 'China', 15000);
INSERT INTO product (name, brand, made_in, price) VALUES ('MacBook Pro2', 'Apple', 'China', 15000);
INSERT INTO product (name, brand, made_in, price) VALUES ('iPad Air', 'Apple', 'China', 500);
INSERT INTO product (name, brand, made_in, price) VALUES ('iPad Pro', 'Apple', 'China', 800);
INSERT INTO product (name, brand, made_in, price) VALUES ('소나타', 'Hyundai', 'Japan', 20000);
INSERT INTO product (name, brand, made_in, price) VALUES ('그랜저', 'Hyundai', 'Japan', 30000);
INSERT INTO product (name, brand, made_in, price) VALUES ('제너시스', 'Hyundai', 'Japan', 50000);
INSERT INTO product (name, brand, made_in, price) VALUES ('에쿠스', 'Hyundai', 'Japan', 60000);
INSERT INTO product (name, brand, made_in, price) VALUES ('Accord', 'Honda', 'Japan', 25000);
INSERT INTO product (name, brand, made_in, price) VALUES ('sienna', 'Honda', 'Japan', 40000);
INSERT INTO product (name, brand, made_in, price) VALUES ('Camry', 'Toyota', 'Japan', 25000);
INSERT INTO product (name, brand, made_in, price) VALUES ('Lexus', 'Toyota', 'Japan', 50000);