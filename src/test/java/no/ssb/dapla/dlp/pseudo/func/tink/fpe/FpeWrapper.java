package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import no.ssb.crypto.tink.fpe.Fpe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class FpeWrapper {

    private final KeysetHandle keysetHandle;

    private final Fpe fpe;

    public FpeWrapper() {
        try {
            this.keysetHandle = KeysetHandle.generateNew(KeyTemplates.get("FPE_FF31_256_ALPHANUMERIC"));
            this.fpe = keysetHandle.getPrimitive(Fpe.class);
        }
        catch (GeneralSecurityException e) {
            throw new FpeWrapperException("Error initializing FpeWrapper for testing", e);
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
            throw new FpeWrapperException("Error deducing keyJson", e);
        }
    }

    public String getKeyId() {
        try {
            return String.valueOf(keysetHandle.primaryKey().getId());
        }
        catch (GeneralSecurityException e) {
            throw new FpeWrapperException("Error deducing keyId", e);
        }
    }

    public Fpe getFpe() {
        return fpe;
    }

    public static class FpeWrapperException extends RuntimeException {
        public FpeWrapperException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
