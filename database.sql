-- =========================================================
-- BT1 - LTWeb : MySQL schema + seed
-- Chay: mysql -u root -p < database.sql
-- =========================================================
DROP DATABASE IF EXISTS servletcrudmvc;
CREATE DATABASE servletcrudmvc CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE servletcrudmvc;

-- Bang Category (CRUD)
CREATE TABLE category (
    cate_id   INT AUTO_INCREMENT PRIMARY KEY,
    cate_name VARCHAR(255) NOT NULL,
    icons     VARCHAR(255) NULL
);

INSERT INTO category (cate_name, icons) VALUES
('Dien thoai', NULL),
('Laptop', NULL),
('Phu kien', NULL);

-- Bang User (Login Cookie / Login Session)
CREATE TABLE users (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(255) NULL,
    username    VARCHAR(100) NOT NULL UNIQUE,
    fullname    VARCHAR(255) NULL,
    password    VARCHAR(255) NOT NULL,
    avatar      VARCHAR(255) NULL,
    roleid      INT NOT NULL DEFAULT 5,
    phone       VARCHAR(20)  NULL,
    createddate DATE NULL,
    is_active   TINYINT(1) DEFAULT 0,
    otp_code    VARCHAR(6) NULL,
    otp_expired DATETIME NULL
);

-- Tai khoan demo: trung / 123
INSERT INTO users (email, username, fullname, password, avatar, roleid, phone, createddate, is_active)
VALUES ('trung@iotstar.vn', 'trung', 'Nguyen Huu Trung', '123', NULL, 1, '0908617108', CURDATE(), 1);

-- Bang Products (1-n voi Category)
CREATE TABLE products (
    product_id   INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description  TEXT NULL,
    price        DECIMAL(10,2) NOT NULL,
    image        VARCHAR(255) NULL,
    cate_id      INT NOT NULL,
    status       INT DEFAULT 1,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cate_id) REFERENCES category(cate_id)
);

-- Du lieu mau cho products
INSERT INTO products (product_name, description, price, image, cate_id, status, created_date) VALUES
('iPhone 15 Pro Max', 'Dien thoai cao cap tu Apple', 29990000, NULL, 1, 1, NOW()),
('Samsung Galaxy S24 Ultra', 'Flagship cua Samsung', 27990000, NULL, 1, 1, NOW()),
('MacBook Pro M3', 'Laptop cao cap cho dev', 45990000, NULL, 2, 1, NOW()),
('Dell XPS 15', 'Laptop cho doanh nhan', 35990000, NULL, 2, 1, NOW()),
('AirPods Pro 2', 'Tai nghe khong day', 5990000, NULL, 3, 1, NOW()),
('Logitech MX Master 3S', 'Chuot khong day cao cap', 2490000, NULL, 3, 1, NOW()),
('iPhone 14 Pro', 'The he truoc iPhone 15', 24990000, NULL, 1, 1, NOW()),
('Xiaomi 13 Pro', 'Dien thoai Xiaomi flagship', 18990000, NULL, 1, 1, NOW()),
('Asus ROG Zephyrus G14', 'Laptop gaming', 42990000, NULL, 2, 1, NOW()),
('iPad Pro M2', 'May tinh bang cao cap', 25990000, NULL, 3, 1, NOW()),
('Samsung Galaxy Tab S9', 'May tinh bang Android', 19990000, NULL, 3, 1, NOW()),
('Sony WH-1000XM5', 'Tai nghe chong on', 7990000, NULL, 3, 1, NOW());
