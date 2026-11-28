-- DIA portal promo tables feeding the Capacity Building-P sheet (Annexure XVI).
-- Column definitions mirror Table_Details.xlsx (PROMO_EVENT_DETAILS / PROMO_WEBINAR_DTLS).

CREATE TABLE PROMO_EVENT_DETAILS (
    PED_EVENT_ID              NUMBER          NOT NULL,
    PED_USER_ID               NUMBER,
    PED_EVENT_NAME            VARCHAR2(1000 BYTE),
    PED_ORGANIZER_NAME        VARCHAR2(500 BYTE),
    PED_VENUE_ADDR            VARCHAR2(500 BYTE),
    PED_STATE                 VARCHAR2(100 BYTE),
    PED_DISTRICT              VARCHAR2(100 BYTE),
    PED_PINCODE               NUMBER,
    PED_EVENT_START_DATE_TIME TIMESTAMP(6),
    PED_EVENT_END_DATE_TIME   TIMESTAMP(6),
    PED_ORGANIZER_CONTACT_NO  VARCHAR2(60 BYTE),
    PED_UPLOAD_BROCHURE       BLOB,
    PED_EVENT_DESCRIPTION     VARCHAR2(4000 BYTE),
    PED_EVENT_PERFOMANCE      VARCHAR2(4000 BYTE),
    PED_COVER_IMAGE           BLOB,
    PED_NOTIFICATION          VARCHAR2(3 BYTE),
    PED_CREATED_ON            DATE,
    BROCHURE_URL              VARCHAR2(200 BYTE),
    COVER_IMAGE_URL           VARCHAR2(200 BYTE),
    TOTAL_PARTICIPANTS        NUMBER,
    UPDATED_ON                TIMESTAMP(6),
    PED_SIDBI_EVENT           VARCHAR2(3 BYTE),
    CONSTRAINT PK_PROMO_EVENT_DETAILS PRIMARY KEY (PED_EVENT_ID)
);

CREATE TABLE PROMO_WEBINAR_DTLS (
    WEBINAR_ID         NUMBER          NOT NULL,
    USER_ID            NUMBER,
    WEBINAR_NAME       VARCHAR2(1000 BYTE),
    ORGANIZER_NAME     VARCHAR2(500 BYTE),
    DATE_TIME_WEBINAR  TIMESTAMP(6),
    SPEAKERS_EXPERT    VARCHAR2(500 BYTE),
    WEBINAR_DESC       VARCHAR2(4000 BYTE),
    WEBINAR_LINK       VARCHAR2(1000 BYTE),
    COVER_IMAGE_URL    VARCHAR2(500 BYTE),
    TOTAL_PARTICIPANTS NUMBER,
    STATUS             VARCHAR2(3 BYTE),
    CREATED_ON         TIMESTAMP(6),
    UPDATED_ON         TIMESTAMP(6),
    UPDATED_BY         NUMBER,
    SIDBI_WEBINAR      VARCHAR2(20 BYTE),
    WEBINAR_END_DATE   TIMESTAMP(6),
    CONSTRAINT PK_PROMO_WEBINAR_DTLS PRIMARY KEY (WEBINAR_ID)
);

-- Sample events (1 and 2 are the rows from Table_Details.xlsx)
INSERT INTO PROMO_EVENT_DETAILS (PED_EVENT_ID, PED_USER_ID, PED_EVENT_NAME, PED_ORGANIZER_NAME, PED_VENUE_ADDR, PED_STATE, PED_DISTRICT, PED_PINCODE, PED_EVENT_START_DATE_TIME, PED_EVENT_END_DATE_TIME, PED_ORGANIZER_CONTACT_NO, PED_EVENT_DESCRIPTION, PED_EVENT_PERFOMANCE, PED_NOTIFICATION, PED_CREATED_ON, BROCHURE_URL, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, UPDATED_ON, PED_SIDBI_EVENT)
VALUES (1, 1, 'National Conclave of Industry Associations- 2nd Edition', 'SIDBI', 'Bharat Mandapam-New Delhi', 'DELHI/NCT OF DELHI', 'CENTRAL DELHI', 110001,
        TIMESTAMP '2026-10-12 09:00:00', TIMESTAMP '2026-10-12 17:30:00', '9999999999',
        'We are pleased to inform you that Small Industries Development Bank of India (SIDBI) is organizing a National Conclave of Industry Associations (2nd Edition) at Bharat Mandapam, New Delhi.',
        'NA', 'Yes', TO_DATE('2026-09-30 15:45:42', 'YYYY-MM-DD HH24:MI:SS'), NULL,
        'https://dia-msme.in/SidbiPromoApis/fileServer/EVENT/226/CoverImage.jpg', 300, TIMESTAMP '2026-09-30 15:46:11.711', 'Yes');

INSERT INTO PROMO_EVENT_DETAILS (PED_EVENT_ID, PED_USER_ID, PED_EVENT_NAME, PED_ORGANIZER_NAME, PED_VENUE_ADDR, PED_STATE, PED_DISTRICT, PED_PINCODE, PED_EVENT_START_DATE_TIME, PED_EVENT_END_DATE_TIME, PED_ORGANIZER_CONTACT_NO, PED_EVENT_DESCRIPTION, PED_EVENT_PERFOMANCE, PED_NOTIFICATION, PED_CREATED_ON, BROCHURE_URL, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, UPDATED_ON, PED_SIDBI_EVENT)
VALUES (2, 1, 'Learnshop on Institutional Strengthening of Industry Associations', 'GT', 'Venue: OASME Conference Hall, Cuttack, Odisha.', 'ODISHA', 'CUTTACK', 753001,
        TIMESTAMP '2026-09-11 09:30:00', TIMESTAMP '2026-09-11 14:30:00', '9999999999',
        'Greetings from DIA Intervention! We are pleased to invite you to the Learnshop on Institutional Strengthening of Industry Associations. The session will be held in Hybrid mode - join in person or online.',
        NULL, 'Yes', TO_DATE('2026-09-09 14:41:05', 'YYYY-MM-DD HH24:MI:SS'),
        'https://dia-msme.in/SidbiPromoApis/fileServer/EVENT/224/Brochure.pdf',
        'https://dia-msme.in/SidbiPromoApis/fileServer/EVENT/224/CoverImage.jpg', 1, TIMESTAMP '2026-09-09 14:42:09.387', 'Yes');

INSERT INTO PROMO_EVENT_DETAILS (PED_EVENT_ID, PED_USER_ID, PED_EVENT_NAME, PED_ORGANIZER_NAME, PED_VENUE_ADDR, PED_STATE, PED_DISTRICT, PED_PINCODE, PED_EVENT_START_DATE_TIME, PED_EVENT_END_DATE_TIME, PED_ORGANIZER_CONTACT_NO, PED_EVENT_DESCRIPTION, PED_EVENT_PERFOMANCE, PED_NOTIFICATION, PED_CREATED_ON, BROCHURE_URL, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, UPDATED_ON, PED_SIDBI_EVENT)
VALUES (3, 1, 'Workshop on Energy Efficiency in Pharma MSMEs', 'GT', 'FTCCI Auditorium, Red Hills, Hyderabad', 'Telangana', 'Hyderabad', 500004,
        TIMESTAMP '2026-10-22 10:00:00', TIMESTAMP '2026-10-22 16:00:00', '9999999999',
        'Hands-on workshop on energy audits, waste heat recovery and solar rooftop financing for pharma cluster units.',
        NULL, 'Yes', TO_DATE('2026-10-10 11:05:00', 'YYYY-MM-DD HH24:MI:SS'), NULL, NULL, 85, TIMESTAMP '2026-10-23 10:15:00', 'Yes');

INSERT INTO PROMO_EVENT_DETAILS (PED_EVENT_ID, PED_USER_ID, PED_EVENT_NAME, PED_ORGANIZER_NAME, PED_VENUE_ADDR, PED_STATE, PED_DISTRICT, PED_PINCODE, PED_EVENT_START_DATE_TIME, PED_EVENT_END_DATE_TIME, PED_ORGANIZER_CONTACT_NO, PED_EVENT_DESCRIPTION, PED_EVENT_PERFOMANCE, PED_NOTIFICATION, PED_CREATED_ON, BROCHURE_URL, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, UPDATED_ON, PED_SIDBI_EVENT)
VALUES (4, 1, 'Export Readiness Training for Chikankari Artisans', 'SIDBI', 'IIA Bhawan, Vibhuti Khand, Lucknow', 'Uttar Pradesh', 'Lucknow', 226010,
        TIMESTAMP '2026-11-05 10:30:00', TIMESTAMP '2026-11-06 13:00:00', '9999999999',
        'Two-day training on export documentation, IEC registration, GeM and e-commerce onboarding for handicraft MSMEs.',
        NULL, 'Yes', TO_DATE('2026-10-25 09:30:00', 'YYYY-MM-DD HH24:MI:SS'), NULL, NULL, 42, TIMESTAMP '2026-11-07 12:00:00', 'No');

-- Sample webinars (1 and 2 are the rows from Table_Details.xlsx; 3 is inactive and must be excluded)
INSERT INTO PROMO_WEBINAR_DTLS (WEBINAR_ID, USER_ID, WEBINAR_NAME, ORGANIZER_NAME, DATE_TIME_WEBINAR, SPEAKERS_EXPERT, WEBINAR_DESC, WEBINAR_LINK, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, STATUS, CREATED_ON, UPDATED_ON, UPDATED_BY, SIDBI_WEBINAR, WEBINAR_END_DATE)
VALUES (1, 1, 'TReDS for MSMEs: Faster Payments, Better Liquidity and Stronger Businesses', 'GT', TIMESTAMP '2026-11-23 16:00:00',
        'Mr. Abhay Emani Relationship Manager - Government Business, Invoice mart.',
        'Dear Partner MSME members, We cordially invite you to join the National Webinar on "TReDS for MSMEs: Faster Payments, Better Liquidity and Stronger Businesses".',
        'https://gtindia.zoom.us/webinar/register/WN_wpRMpK4SRK64dHUlQ9S_aQ',
        'https://dia-msme.in/SidbiPromoApis/fileServer/Webinar/73/CoverImage.jpg', 1, 'Y',
        TIMESTAMP '2026-11-16 14:38:34.020', TIMESTAMP '2026-11-16 14:39:22.868', NULL, 'Yes', TIMESTAMP '2026-11-23 17:00:00');

INSERT INTO PROMO_WEBINAR_DTLS (WEBINAR_ID, USER_ID, WEBINAR_NAME, ORGANIZER_NAME, DATE_TIME_WEBINAR, SPEAKERS_EXPERT, WEBINAR_DESC, WEBINAR_LINK, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, STATUS, CREATED_ON, UPDATED_ON, UPDATED_BY, SIDBI_WEBINAR, WEBINAR_END_DATE)
VALUES (2, 1, 'ZED Certification - Enhancing Quality & Competitiveness for MSMEs.', 'GT', TIMESTAMP '2026-11-19 16:00:00',
        'Mr. A.K. Gautam IEDS, Assistant Director Grade-I, MSME DFO Solan, Himachal Pradesh, Ministry of MSME',
        'Dear Industry Members, Join us for an insightful webinar on ZED (Zero Defect Zero Effect) Certification, a flagship initiative of the Ministry of MSME.',
        'https://gtindia.zoom.us/webinar/register/WN_wCxuZPscSwiuEaAXMYfSKA',
        'https://dia-msme.in/SidbiPromoApis/fileServer/Webinar/72/CoverImage.jpg', 1, 'Y',
        TIMESTAMP '2026-11-16 14:13:38.750', TIMESTAMP '2026-11-16 14:45:51.207', NULL, 'Yes', TIMESTAMP '2026-11-19 17:00:00');

INSERT INTO PROMO_WEBINAR_DTLS (WEBINAR_ID, USER_ID, WEBINAR_NAME, ORGANIZER_NAME, DATE_TIME_WEBINAR, SPEAKERS_EXPERT, WEBINAR_DESC, WEBINAR_LINK, COVER_IMAGE_URL, TOTAL_PARTICIPANTS, STATUS, CREATED_ON, UPDATED_ON, UPDATED_BY, SIDBI_WEBINAR, WEBINAR_END_DATE)
VALUES (3, 1, 'Cancelled: GST Compliance Updates for Micro Enterprises', 'GT', TIMESTAMP '2026-11-12 15:00:00',
        'CA Ritu Sharma', 'This webinar has been withdrawn.', NULL, NULL, 0, 'N',
        TIMESTAMP '2026-11-01 10:00:00', TIMESTAMP '2026-11-10 10:00:00', NULL, 'Yes', TIMESTAMP '2026-11-12 16:00:00');

INSERT INTO endpoint_role_policy (policy_key, roles_csv, description, updated_at) VALUES ('capacityBuildingRead', 'GT_FIELD_TEAM,GT_PMU,SIDBI_RO,SIDBI_HO_MAKER,SIDBI_HO_CHECKER', 'View Capacity Building-P monitoring report', SYSTIMESTAMP);

COMMIT;
