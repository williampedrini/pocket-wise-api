package com.pocketwise.application.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.jasypt.encryption.StringEncryptor;

import com.pocketwise.application.common.configuration.SpringContext;

@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    private StringEncryptor getEncryptor() {
        return SpringContext.getBean(StringEncryptor.class);
    }

    @Override
    public String convertToDatabaseColumn(final String value) {
        if (value == null) {
            return null;
        }
        return getEncryptor().encrypt(value);
    }

    @Override
    public String convertToEntityAttribute(final String value) {
        if (value == null) {
            return null;
        }
        return getEncryptor().decrypt(value);
    }
}
