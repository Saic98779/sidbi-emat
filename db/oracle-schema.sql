-- Oracle Database Schema for EMAT Application
-- Generated for EMAT (Enterprise Management Application Tool)
-- Target: Oracle Database (XEPDB1)
-- Date: 2026-07-23

-- =====================================================
-- Users Table
-- =====================================================
CREATE TABLE users (
    id NUMBER(19) PRIMARY KEY,
    username VARCHAR2(100) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    email VARCHAR2(100) NOT NULL UNIQUE,
    first_name VARCHAR2(100),
    last_name VARCHAR2(100),
    role VARCHAR2(50) NOT NULL,
    is_active NUMBER(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP
);

-- =====================================================
-- Create Sequence for auto-increment
-- =====================================================
CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

-- =====================================================
-- Create Trigger for ID generation
-- =====================================================
CREATE OR REPLACE TRIGGER users_id_trigger
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SELECT users_seq.NEXTVAL
    INTO :new.id
    FROM dual;
END;
/

-- =====================================================
-- Create Indexes for Performance
-- =====================================================
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_is_active ON users(is_active);

-- =====================================================
-- Insert Sample Data
-- =====================================================
INSERT INTO users (username, password, email, first_name, last_name, role, is_active)
VALUES ('admin', 'admin123', 'admin@emat.com', 'Admin', 'User', 'DIA', 1);

INSERT INTO users (username, password, email, first_name, last_name, role, is_active)
VALUES ('bse_user', 'bse123', 'bse@emat.com', 'BSE', 'Analyst', 'BSE', 1);

INSERT INTO users (username, password, email, first_name, last_name, role, is_active)
VALUES ('field_team', 'field123', 'field@emat.com', 'Field', 'Team', 'GT_FIELD_TEAM', 1);

INSERT INTO users (username, password, email, first_name, last_name, role, is_active)
VALUES ('pmu_user', 'pmu123', 'pmu@emat.com', 'PMU', 'Officer', 'GT_PMU', 1);

INSERT INTO users (username, password, email, first_name, last_name, role, is_active)
VALUES ('sidbi_sde', 'sidbi123', 'sde@sidbi.com', 'SIDBI', 'SDE', 'SIDBI_SDE', 1);

COMMIT;

-- =====================================================
-- View to display active users
-- =====================================================
CREATE OR REPLACE VIEW active_users AS
SELECT id, username, email, first_name, last_name, role, created_at, updated_at
FROM users
WHERE is_active = 1;

-- =====================================================
-- View to display users by role
-- =====================================================
CREATE OR REPLACE VIEW users_by_role AS
SELECT role, COUNT(*) as user_count
FROM users
WHERE is_active = 1
GROUP BY role
ORDER BY role;

-- =====================================================
-- Queries for verification
-- =====================================================
-- SELECT COUNT(*) FROM users;
-- SELECT * FROM active_users;
-- SELECT * FROM users_by_role;

