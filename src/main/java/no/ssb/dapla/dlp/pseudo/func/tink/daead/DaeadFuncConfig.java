package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class DaeadFuncConfig {
    private final String dataEncryptionKeyId;
    private final String base64EncodedWrappedDataEncryptionKey;
    private final DeterministicAead daead;

    @UtilityClass
    public static class Param {
        public static final String DEK_ID = "dataEncryptionKeyId";
        public static final String WDEK = "wrappedDataEncryptionKey";
        public static final String DAEAD = "deterministicAead";
    }
}
