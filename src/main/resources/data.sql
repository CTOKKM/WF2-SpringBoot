-- ✅ roles 테이블에 'USER', 'ADMIN' 중복 없이 삽입
INSERT INTO roles (name)
SELECT * FROM (SELECT 'USER') AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'USER'
);

INSERT INTO roles (name)
SELECT * FROM (SELECT 'ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ADMIN'
);

-- ✅ admin@example.com 유저가 없을 경우만 삽입
INSERT INTO users (email, password, name)
SELECT * FROM (
    SELECT 'admin@example.com',
           '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi',
           'Admin User'
) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@example.com'
);

-- ✅ ADMIN 역할 연결 (user_roles 테이블에 중복 없이)
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@example.com' AND r.name = 'ADMIN'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles
    WHERE user_id = u.id AND role_id = r.id
);

-- ✅ USER 역할 연결
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@example.com' AND r.name = 'USER'
  AND NOT EXISTS (
    SELECT 1 FROM user_roles
    WHERE user_id = u.id AND role_id = r.id
);

-- ✅ product 샘플 데이터 삽입 (중복 체크는 생략 – 필요 시 추가 가능)
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S6', 'Samsung Corp', 'Korea', 600.0);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S8', 'Samsung Corp', 'Korea', 800.0);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S10', 'Samsung Corp', 'Korea', 1000.0);
INSERT INTO product (name, brand, made_in, price) VALUES ('Galaxy S21', 'Samsung Corp', 'Korea', 1000.0);
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