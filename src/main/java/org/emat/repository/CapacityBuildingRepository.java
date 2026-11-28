package org.emat.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.emat.dto.CapacityBuildingResponse;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/** Reads Capacity Building-P rows from the DIA portal promo tables with a plain SELECT. */
@Repository
@RequiredArgsConstructor
public class CapacityBuildingRepository {

    private static final String BASE_QUERY =
            """
            SELECT ROW_NUMBER() OVER (ORDER BY cb.event_date, cb.source, cb.source_id) AS sn,
                   cb.*
            FROM (
                SELECT 'EVENT'                                   AS source,
                       e.PED_EVENT_ID                            AS source_id,
                       (SELECT MIN(ro.RO_NAME) KEEP (DENSE_RANK FIRST ORDER BY
                                   CASE WHEN UPPER(TRIM(ro.DISTRICT)) = UPPER(TRIM(e.PED_DISTRICT))
                                        THEN 0 ELSE 1 END)
                          FROM REGIONAL_OFFICE ro
                         WHERE ro.IS_ACTIVE = 1
                           AND UPPER(TRIM(ro.STATE)) = UPPER(TRIM(e.PED_STATE))) AS name_of_ro,
                       e.PED_EVENT_NAME                          AS event_name,
                       e.PED_EVENT_START_DATE_TIME               AS event_date,
                       e.PED_EVENT_END_DATE_TIME                 AS event_end_date,
                       ROUND((CAST(e.PED_EVENT_END_DATE_TIME AS DATE)
                              - CAST(e.PED_EVENT_START_DATE_TIME AS DATE)) * 1440) AS duration_minutes,
                       CASE WHEN UPPER(e.PED_EVENT_DESCRIPTION) LIKE '%HYBRID%' THEN 'Hybrid'
                            ELSE 'Physical' END                  AS nature,
                       CASE WHEN UPPER(e.PED_EVENT_NAME) LIKE '%WEBINAR%'   THEN 'Webinar'
                            WHEN UPPER(e.PED_EVENT_NAME) LIKE '%TRAINING%'  THEN 'Training'
                            WHEN UPPER(e.PED_EVENT_NAME) LIKE '%WORKSHOP%'
                              OR UPPER(e.PED_EVENT_NAME) LIKE '%LEARNSHOP%' THEN 'Workshop'
                            ELSE 'Awareness' END                 AS event_type,
                       e.PED_EVENT_DESCRIPTION                   AS description,
                       e.PED_SIDBI_EVENT                         AS part_of_dia,
                       CAST(NULL AS VARCHAR2(500))               AS trainer,
                       e.TOTAL_PARTICIPANTS                      AS attendees,
                       e.PED_STATE                               AS state,
                       e.PED_DISTRICT                            AS district
                  FROM PROMO_EVENT_DETAILS e
                UNION ALL
                SELECT 'WEBINAR',
                       w.WEBINAR_ID,
                       NULL,
                       w.WEBINAR_NAME,
                       w.DATE_TIME_WEBINAR,
                       w.WEBINAR_END_DATE,
                       ROUND((CAST(w.WEBINAR_END_DATE AS DATE)
                              - CAST(w.DATE_TIME_WEBINAR AS DATE)) * 1440),
                       'Virtual',
                       'Webinar',
                       w.WEBINAR_DESC,
                       w.SIDBI_WEBINAR,
                       w.SPEAKERS_EXPERT,
                       w.TOTAL_PARTICIPANTS,
                       NULL,
                       NULL
                  FROM PROMO_WEBINAR_DTLS w
                 WHERE w.STATUS = 'Y'
            ) cb
            WHERE 1 = 1
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<CapacityBuildingResponse> findAll(
            LocalDate fromDate, LocalDate toDate, String source) {
        StringBuilder sql = new StringBuilder(BASE_QUERY);
        MapSqlParameterSource params = new MapSqlParameterSource();
        if (fromDate != null) {
            sql.append(" AND cb.event_date >= :fromDate");
            params.addValue("fromDate", Timestamp.valueOf(fromDate.atStartOfDay()));
        }
        if (toDate != null) {
            sql.append(" AND cb.event_date < :toDate");
            params.addValue("toDate", Timestamp.valueOf(toDate.plusDays(1).atStartOfDay()));
        }
        if (source != null) {
            sql.append(" AND cb.source = :source");
            params.addValue("source", source);
        }
        sql.append(" ORDER BY sn");
        return new ArrayList<>(jdbcTemplate.query(sql.toString(), params, this::mapRow));
    }

    private CapacityBuildingResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long durationMinutes = getLong(rs, "duration_minutes");
        return CapacityBuildingResponse.builder()
                .source(rs.getString("source"))
                .sourceId(getLong(rs, "source_id"))
                .sn(getLong(rs, "sn"))
                .nameOfRo(rs.getString("name_of_ro"))
                .eventName(rs.getString("event_name"))
                .eventDate(toLocalDateTime(rs.getTimestamp("event_date")))
                .eventEndDate(toLocalDateTime(rs.getTimestamp("event_end_date")))
                .durationMinutes(durationMinutes)
                .duration(formatDuration(durationMinutes))
                .nature(rs.getString("nature"))
                .eventType(rs.getString("event_type"))
                .description(rs.getString("description"))
                .partOfDia(rs.getString("part_of_dia"))
                .trainerOrBdsProvider(rs.getString("trainer"))
                .attendees(getLong(rs, "attendees"))
                .uploadedOnDiaPortal("Yes")
                .state(rs.getString("state"))
                .district(rs.getString("district"))
                .build();
    }

    private static Long getLong(ResultSet rs, String column) throws SQLException {
        long value = rs.getLong(column);
        return rs.wasNull() ? null : value;
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    private static String formatDuration(Long minutes) {
        if (minutes == null || minutes < 0) {
            return null;
        }
        long hours = minutes / 60;
        long mins = minutes % 60;
        return mins == 0 ? hours + " hrs" : hours + " hrs " + mins + " mins";
    }
}
