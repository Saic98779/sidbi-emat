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
-- BSE Recommendation Table
-- =====================================================
CREATE TABLE ia_bse_recommendation (
    uuid RAW(16) DEFAULT SYS_GUID() PRIMARY KEY,
    registration_uuid RAW(16) NOT NULL,
    state VARCHAR2(100),
    district VARCHAR2(100),
    industry_registration_id VARCHAR2(200),
    bse_name VARCHAR2(200),
    mobile_number VARCHAR2(15),
    email_id VARCHAR2(200),
    highest_qualification VARCHAR2(200),
    experience_status NUMBER(1),
    experience_years NUMBER(10),
    experience_months NUMBER(10),
    employment_status VARCHAR2(50),
    current_salary NUMBER(12,2),
    notice_period_days NUMBER(10),
    last_drawn_salary NUMBER(12,2),
    relieving_letter VARCHAR2(500),
    expected_salary NUMBER(12,2),
    resume_status VARCHAR2(50),
    resume_file VARCHAR2(500),
    salary_slip VARCHAR2(500),
    candidate_cv VARCHAR2(500),
    gt_recommendation VARCHAR2(50),
    gt_recommendation_date DATE,
    gt_remarks VARCHAR2(1000),
    pmu_recommendation VARCHAR2(50),
    pmu_recommendation_date DATE,
    pmu_remarks VARCHAR2(1000),
    ho_recommendation VARCHAR2(50),
    ho_recommendation_date DATE,
    ho_remarks VARCHAR2(1000),
    committee_recommendation VARCHAR2(50),
    committee_date DATE,
    committee_mom VARCHAR2(500),
    committee_remarks VARCHAR2(1000),
    approved_salary NUMBER(12,2),
    approved_travel_allowance NUMBER(12,2),
    date_of_joining DATE,
    ia_mapped NUMBER(1),
    offer_letter VARCHAR2(500),
    is_active NUMBER(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    updated_at TIMESTAMP DEFAULT SYSTIMESTAMP,
    created_by VARCHAR2(100),
    updated_by VARCHAR2(100)
);

-- =====================================================
-- Activity Table
-- =====================================================
CREATE TABLE ACTIVITY (
    ACTIVITY_ID NUMBER(19, 0) GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    ACTIVITY_TYPE VARCHAR2(255),
    DETAILS CLOB,
    DATE_TIME TIMESTAMP,
    FOLLOW_UP_REQ NUMBER(1, 0),
    FOLLOW_UP_ID NUMBER(19, 0),
    LOCATION_DETAILS VARCHAR2(1000),
    CREATED_USER_ID NUMBER(19, 0),
    CREATED_DT_STAMP TIMESTAMP,
    APPROVED_DT_STAMP TIMESTAMP,
    BSE_ID NUMBER(19, 0),
    GT_ID NUMBER(19, 0)
);

CREATE TABLE ACTIVITY_STATUS (
    STATUS_ID NUMBER(19, 0) GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    ACTIVITY_ID NUMBER(19, 0) NOT NULL,
    FOLLOWUP_ACTIVITY_ID NUMBER(19, 0),
    STATUS VARCHAR2(255),
    STATUS_UPDATED_BY_ROLE VARCHAR2(50),
    STATUS_APPROVAL_REQUIRED NUMBER(1, 0),
    STATUS_UPDATED_DT_STAMP TIMESTAMP,
    STATUS_REMARKS VARCHAR2(1000),
    CONSTRAINT FK_ACTIVITY_STATUS_ACTIVITY FOREIGN KEY (ACTIVITY_ID) REFERENCES ACTIVITY (ACTIVITY_ID)
);

-- =====================================================
-- Queries for verification
-- =====================================================
-- SELECT COUNT(*) FROM users;
-- SELECT * FROM active_users;
-- SELECT * FROM users_by_role;
