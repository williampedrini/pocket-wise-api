package com.pocketwise.application.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.pocketwise.application.common.configuration.SpringContext;
import com.pocketwise.application.common.service.EncryptionService;

@Converter
public class EncryptedStringConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(final String value) {
        if (value == null) {
            return null;
        }
        return getEncryptionService().encrypt(value);
    }

    @Override
    public String convertToEntityAttribute(final String value) {
        if (value == null) {
            return null;
        }
        return getEncryptionService().decrypt(value);
    }

    private EncryptionService getEncryptionService() {
        return SpringContext.getBean(EncryptionService.class);
    }
}
