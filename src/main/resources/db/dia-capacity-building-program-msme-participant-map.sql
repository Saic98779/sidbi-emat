-- =====================================================================================
-- DIA Capacity Building Program <-> DIA MSME Participant (Many-to-Many)
--
-- One capacity building program can have many MSME participants and one participant can
-- attend many programs. The unique constraint on (DIA_CAPACITY_BUILDING_PROGRAM_ID,
-- DIA_MSME_PARTICIPANT_ID) guarantees a participant is never duplicated within the same
-- program, even under concurrent imports.
--
-- Run once against the Oracle schema (spring.jpa.hibernate.ddl-auto = none).
-- =====================================================================================

CREATE TABLE DIA_CAPACITY_BUILDING_PROGRAM_MSME_PARTICIPANT_MAP (
    DIA_CAPACITY_BUILDING_PROGRAM_ID NUMBER(19) NOT NULL,
    DIA_MSME_PARTICIPANT_ID          NUMBER(19) NOT NULL,
    CONSTRAINT UK_DIA_CBP_MSME_PARTICIPANT_MAP
        UNIQUE (DIA_CAPACITY_BUILDING_PROGRAM_ID, DIA_MSME_PARTICIPANT_ID),
    CONSTRAINT FK_DIA_CBP_PMAP_PROGRAM
        FOREIGN KEY (DIA_CAPACITY_BUILDING_PROGRAM_ID)
        REFERENCES DIA_CAPACITY_BUILDING_PROGRAM (ID) ON DELETE CASCADE,
    CONSTRAINT FK_DIA_CBP_PMAP_PARTICIPANT
        FOREIGN KEY (DIA_MSME_PARTICIPANT_ID)
        REFERENCES DIA_MSME_PARTICIPANT (ID) ON DELETE CASCADE
);

-- Supports lookups from the participant side (the unique index covers the program side).
CREATE INDEX IDX_DIA_CBP_PMAP_PARTICIPANT
    ON DIA_CAPACITY_BUILDING_PROGRAM_MSME_PARTICIPANT_MAP (DIA_MSME_PARTICIPANT_ID);

-- Backfill: link existing participants to the program stored in the legacy EVENT_ID column.
INSERT INTO DIA_CAPACITY_BUILDING_PROGRAM_MSME_PARTICIPANT_MAP (DIA_CAPACITY_BUILDING_PROGRAM_ID, DIA_MSME_PARTICIPANT_ID)
SELECT p.EVENT_ID, p.ID
FROM DIA_MSME_PARTICIPANT p
WHERE p.EVENT_ID IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM DIA_CAPACITY_BUILDING_PROGRAM_MSME_PARTICIPANT_MAP m
      WHERE m.DIA_CAPACITY_BUILDING_PROGRAM_ID = p.EVENT_ID
        AND m.DIA_MSME_PARTICIPANT_ID = p.ID
  );

COMMIT;
