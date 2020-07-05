package com.toast.cloud.common.jpa.converter;

import javax.persistence.AttributeConverter;

public class YesNoConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return value ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return value.equals("Y");
    }

}