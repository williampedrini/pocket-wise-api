package com.pocketwise.application.common.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.GeneralSecurityException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.pocketwise.application.common.properties.EncryptionProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private final EncryptionProperties properties;

    /**
     * Encrypts the given plaintext using AES encryption.
     *
     * @param value the plaintext to encrypt; if null, returns null.
     * @return the Base64-encoded encrypted string.
     */
    public String encrypt(final String value) {
        if (value == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());
            final byte[] encrypted = cipher.doFinal(value.getBytes(UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (final GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to encrypt value", exception);
        }
    }

    /**
     * Decrypts the given Base64-encoded ciphertext using AES decryption.
     *
     * @param value the Base64-encoded ciphertext to decrypt; if null, returns null.
     * @return the decrypted plaintext string.
     */
    public String decrypt(final String value) {
        if (value == null) {
            return null;
        }
        try {
            final Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            final byte[] decoded = Base64.getDecoder().decode(value);
            final byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, UTF_8);
        } catch (final GeneralSecurityException exception) {
            throw new IllegalStateException("Failed to decrypt value", exception);
        }
    }

    private SecretKeySpec getSecretKey() {
        final byte[] keyBytes = properties.getSecretKey().getBytes(UTF_8);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }
}
