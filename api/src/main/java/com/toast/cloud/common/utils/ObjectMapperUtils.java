package com.toast.cloud.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtils {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.registerModule(new JavaTimeModule());
    }

    public static ObjectMapper get() {
        return MAPPER;
    }

    public static String serialize(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            log.warn("", e);
            return "";
        }
    }

    public static <T> T convert(Object value, Class<T> valueType) {
        try {
            return MAPPER.convertValue(value, valueType);
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

    public static <T> T deserialize(String content, Class<T> valueType) {
        try {
            if (valueType == String.class) {
                return (T) content;
            }

            return MAPPER.readValue(content, valueType);
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

}
