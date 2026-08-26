-- Script tạo CSDL cho Lab 10
CREATE DATABASE IF NOT EXISTS lab10_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lab10_db;

-- Bảng người dùng (Hibernate cũng có thể tự tạo bảng này do hbm2ddl.auto=update,
-- nhưng script này giúp bạn nộp bài đúng yêu cầu "database script")
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    description VARCHAR(500)
);

-- Dữ liệu tài khoản mẫu (mật khẩu lưu dạng đơn giản chỉ phục vụ học tập)
INSERT INTO users (email, password, full_name, role, active) VALUES
('admin@eaut.edu.vn', 'admin123', 'Quản trị viên', 'ADMIN', TRUE),
('staff@eaut.edu.vn', 'staff123', 'Nhân viên nghiệp vụ', 'STAFF', TRUE),
('user@eaut.edu.vn', 'user123', 'Người dùng thường', 'USER', TRUE)
ON DUPLICATE KEY UPDATE email = email;

-- Dữ liệu sản phẩm mẫu
INSERT INTO products (name, price, quantity, description) VALUES
('Bàn phím cơ', 850000, 20, 'Bàn phím cơ switch đỏ'),
('Chuột không dây', 350000, 35, 'Chuột không dây pin AA'),
('Màn hình 24 inch', 3200000, 10, 'Màn hình Full HD 24 inch');
