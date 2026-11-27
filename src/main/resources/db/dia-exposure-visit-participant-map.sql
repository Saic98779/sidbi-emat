-- =====================================================================================
-- DIA Exposure Visit <-> DIA Exposure Visit Participant (Many-to-Many)
--
-- One exposure visit can have many participants and one participant can attend many
-- exposure visits. The unique constraint on (DIA_EXPOSURE_VISIT_ID,
-- DIA_EXPOSURE_VISIT_PARTICIPANT_ID) guarantees a participant is never duplicated
-- within the same exposure visit, even under concurrent imports.
--
-- Run once against the Oracle schema (spring.jpa.hibernate.ddl-auto = none).
-- =====================================================================================

CREATE TABLE DIA_EXPOSURE_VISIT_PARTICIPANT_MAP (
    DIA_EXPOSURE_VISIT_ID             NUMBER(19) NOT NULL,
    DIA_EXPOSURE_VISIT_PARTICIPANT_ID NUMBER(19) NOT NULL,
    CONSTRAINT UK_DIA_EV_PARTICIPANT_MAP
        UNIQUE (DIA_EXPOSURE_VISIT_ID, DIA_EXPOSURE_VISIT_PARTICIPANT_ID),
    CONSTRAINT FK_DIA_EV_PMAP_VISIT
        FOREIGN KEY (DIA_EXPOSURE_VISIT_ID)
        REFERENCES DIA_EXPOSURE_VISIT (ID) ON DELETE CASCADE,
    CONSTRAINT FK_DIA_EV_PMAP_PARTICIPANT
        FOREIGN KEY (DIA_EXPOSURE_VISIT_PARTICIPANT_ID)
        REFERENCES DIA_EXPOSURE_VISIT_PARTICIPANT (ID) ON DELETE CASCADE
);

-- Supports lookups from the participant side (the unique index covers the visit side).
CREATE INDEX IDX_DIA_EV_PMAP_PARTICIPANT
    ON DIA_EXPOSURE_VISIT_PARTICIPANT_MAP (DIA_EXPOSURE_VISIT_PARTICIPANT_ID);

-- Backfill: link existing participants to the visit stored in the legacy EXPOSURE_VISIT_ID column.
INSERT INTO DIA_EXPOSURE_VISIT_PARTICIPANT_MAP (DIA_EXPOSURE_VISIT_ID, DIA_EXPOSURE_VISIT_PARTICIPANT_ID)
SELECT p.EXPOSURE_VISIT_ID, p.ID
FROM DIA_EXPOSURE_VISIT_PARTICIPANT p
WHERE p.EXPOSURE_VISIT_ID IS NOT NULL
  AND NOT EXISTS (
      SELECT 1 FROM DIA_EXPOSURE_VISIT_PARTICIPANT_MAP m
      WHERE m.DIA_EXPOSURE_VISIT_ID = p.EXPOSURE_VISIT_ID
        AND m.DIA_EXPOSURE_VISIT_PARTICIPANT_ID = p.ID
  );

COMMIT;
