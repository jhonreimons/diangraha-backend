DROP DATABASE IF EXISTS diangraha_dev;

CREATE DATABASE diangraha_dev;

USE diangraha_dev;
-- Table Admin
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Table Brand
CREATE TABLE brands (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    logo_url TEXT
);

-- Table Service
CREATE TABLE services (
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
    FOREIGN KEY (service_id) REFERENCES services(id) ON DELETE CASCADE
);

-- Table Contact Message
CREATE TABLE contact_messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,          -- selalu ada
    email VARCHAR(255) NOT NULL,              -- selalu ada
    phone_number VARCHAR(50) NULL,            -- opsional
    company_name VARCHAR(255) NULL,           -- opsional (hanya form pertama)
    interested_in VARCHAR(255) NULL,          -- opsional (hanya form kedua)
    message TEXT NULL,                        -- opsional, bisa kosong
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

SELECT * FROM contact_messages;
SHOW TABLES;

