package org.emat.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.emat.enums.BulkMessaging;

/** Persists a list of {@link BulkMessaging} as a comma-separated VARCHAR value. */
@Converter
public class BulkMessagingListConverter
        implements AttributeConverter<List<BulkMessaging>, String> {

    @Override
    public String convertToDatabaseColumn(List<BulkMessaging> attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    @Override
    public List<BulkMessaging> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(BulkMessaging::valueOf)
                .collect(Collectors.toList());
    }
}