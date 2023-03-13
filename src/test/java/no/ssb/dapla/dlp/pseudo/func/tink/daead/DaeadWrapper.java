package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.crypto.tink.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class DaeadWrapper {

    private final KeysetHandle keysetHandle;

    private final DeterministicAead daead;

    public DaeadWrapper() {
        try {
            this.keysetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES256_SIV"));
            this.daead = keysetHandle.getPrimitive(DeterministicAead.class);
        }
        catch (GeneralSecurityException e) {
            throw new DaeadWrapperException("Error initializing DaeadWrapper for testing", e);
        }
    }

    private static String toKeyJson(KeysetHandle keysetHandle) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withOutputStream(baos));
        return new String(baos.toByteArray());
    }

    public String getKeyJson() {
        try {
            return toKeyJson(keysetHandle);
        }
        catch (IOException e) {
            throw new DaeadWrapperException("Error deducing keyJson", e);
        }
    }

    public String getKeyId() {
        try {
            return String.valueOf(keysetHandle.primaryKey().getId());
        }
        catch (GeneralSecurityException e) {
            throw new DaeadWrapperException("Error deducing keyId", e);
        }
    }

    public DeterministicAead getDaead() {
        return daead;
    }

    public static class DaeadWrapperException extends RuntimeException {
        public DaeadWrapperException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
