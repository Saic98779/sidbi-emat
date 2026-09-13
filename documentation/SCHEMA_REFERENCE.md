# Schema Reference

This document captures the consolidated Oracle table schemas for the migrated `Long` + `SEQUENCE` identity model used by the application.

## Conventions

- Primary keys use `ID NUMBER(19)`.
- Oracle sequences are used for auto-generation.
- Triggers populate the ID column when no value is supplied.
- Foreign keys use the corresponding `..._ID` column names.

---

## `INDUSTRY_ASSOCIATION_REGISTRATION`

### Purpose
Stores industry association registration details.

### Primary Key
- `ID NUMBER(19)`

### Sequence
- `SEQ_INDUSTRY_ASSOCIATION_REGISTRATION`

### Trigger
- `INDUSTRY_ASSOCIATION_REGISTRATION_BI`

### Important Columns
- `STATE VARCHAR2(100) NOT NULL`
- `INDUSTRY_ASSOCIATION_NAME VARCHAR2(500) NOT NULL`
- `DISTRICT VARCHAR2(100)`
- `PINCODE VARCHAR2(10)`
- `EMAIL VARCHAR2(100)`
- `PAN_NO VARCHAR2(15)`
- `IS_ACTIVE NUMBER(1) DEFAULT 1`
- `CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`
- `UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`

### Notes
- `SIDBE_APPROVED_BY_USER_ID` references `users(id)`.
- Collection tables continue to use the parent key column name configured in JPA.

---

## `INDUSTRY_ASSOCIATION_APPRAISAL`

### Purpose
Stores appraisal details linked to a single registration.

### Primary Key
- `ID NUMBER(19)`

### Sequence
- `SEQ_INDUSTRY_ASSOCIATION_APPRAISAL`

### Trigger
- `INDUSTRY_ASSOCIATION_APPRAISAL_BI`

### Foreign Key
- `REGISTRATION_ID NUMBER(19) NOT NULL`
- References `INDUSTRY_ASSOCIATION_REGISTRATION(ID)`

### Important Columns
- `CIBIL_REPORT_REFERENCE_NO VARCHAR2(200)`
- `CIBIL_REPORT_DATE DATE`
- `CIBIL_RANKING VARCHAR2(100)`
- `RECOMMENDATION VARCHAR2(100)`
- `IS_ACTIVE NUMBER(1) DEFAULT 1`
- `CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`
- `UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`

### Notes
- `SIDBE_APPROVED_BY_USER_ID` references `users(id)`.
- The registration link is unique, enforcing a one-to-one relationship.

---

## `USERS`

### Primary Key
- `ID NUMBER(19)`

### Sequence
- `users_seq`

### Trigger
- `users_id_trigger`

### Important Columns
- `USERNAME VARCHAR2(100) NOT NULL UNIQUE`
- `EMAIL VARCHAR2(100) NOT NULL UNIQUE`
- `ROLE VARCHAR2(50) NOT NULL`
- `IS_ACTIVE NUMBER(1) DEFAULT 1`
- `CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`
- `UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP`

---

## Quick Relationship Overview

- `INDUSTRY_ASSOCIATION_REGISTRATION.ID` → parent registration record
- `INDUSTRY_ASSOCIATION_APPRAISAL.REGISTRATION_ID` → linked registration
- `users.ID` → user records used for SIDBI approval audit links

---

## Implementation Reminder

If you create or migrate records manually, insert without specifying `ID` unless you explicitly need a fixed value. The trigger will assign the next sequence value automatically.

