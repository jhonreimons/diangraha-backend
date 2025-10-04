
CREATE DATABASE diangraha_dev;

USE diangraha_dev;
-- Table Admin
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Table Brand
CREATE TABLE brand (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    logo_url TEXT
);

-- Table Service
CREATE TABLE service (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    short_desc TEXT,
    long_desc TEXT,
    image_url TEXT
);

-- Table Service Feature (relasi ke Service)
CREATE TABLE service_feature (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    service_id BIGINT NOT NULL,
    feature_name VARCHAR(255) NOT NULL,
    feature_desc TEXT,
    FOREIGN KEY (service_id) REFERENCES service(id) ON DELETE CASCADE
);

-- Table Contact Message
CREATE TABLE contact_message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default user
INSERT INTO users (username, password) VALUES 
('dev', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi2');
