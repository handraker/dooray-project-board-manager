package com.toast.cloud.common.jpa.converter;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public LocalDate convertToEntityAttribute(String localDate) {
        if (localDate == null) {
            return null;
        }
        return LocalDate.parse(localDate, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

}
