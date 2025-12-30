-- AquaGuard Database Setup
-- Run this script in MySQL to create the database and tables

CREATE DATABASE IF NOT EXISTS aquaguard_db;
USE aquaguard_db;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Marine Resources table
CREATE TABLE IF NOT EXISTS marine_resources (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(100) NOT NULL,
    quantity INT NOT NULL
);

-- Monitoring Data table
CREATE TABLE IF NOT EXISTS monitoring_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    metric_name VARCHAR(100) NOT NULL,
    metric_value VARCHAR(255) NOT NULL,
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reports table
CREATE TABLE IF NOT EXISTS reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    report_type VARCHAR(50) NOT NULL,
    generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    title VARCHAR(255),
    content TEXT
);

-- Sample Data
INSERT INTO users (email, password, name, role) VALUES
('admin@aquaguard.com', 'admin123', 'System Admin', 'admin'),
('staff@aquaguard.com', 'staff123', 'Marine Staff', 'staff'),
('researcher@aquaguard.com', 'res123', 'Researcher', 'researcher')
ON DUPLICATE KEY UPDATE email=email;

INSERT INTO marine_resources (name, type, quantity) VALUES
('Tuna', 'Fish', 120),
('Great Barrier Reef Coral', 'Coral', 75),
('Seawater Sample', 'Water Quality', 1),
('Clownfish', 'Fish', 45),
('Mangrove', 'Flora', 30)
ON DUPLICATE KEY UPDATE name=name;

INSERT INTO monitoring_data (metric_name, metric_value) VALUES
('Active Sensors', '52'),
('Data Received (per day)', '1,320 records'),
('System Status', 'Operational')
ON DUPLICATE KEY UPDATE metric_name=metric_name;

INSERT INTO reports (report_type, title) VALUES
('daily', 'Daily Marine Report'),
('daily', 'Daily Sensor Summary'),
('daily', 'Daily Energy Report'),
('weekly', 'Weekly Ecosystem Overview'),
('weekly', 'Weekly Compliance Report'),
('monthly', 'Monthly Sustainability Metrics')
ON DUPLICATE KEY UPDATE report_type=report_type;