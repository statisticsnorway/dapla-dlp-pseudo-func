package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class TinkDaeadFuncConfig {
    private final String dataEncryptionKeyId;
    private final String base64EncodedWrappedDataEncryptionKey;
    private final DeterministicAead daead;

    @UtilityClass
    public static class Param {
        public static final String DAEAD = "deterministicAead";
        public static final String KEY_ID = "keyId";
    }
}
