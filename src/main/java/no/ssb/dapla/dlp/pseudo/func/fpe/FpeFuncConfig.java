package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.config.Alphabet;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class FpeFuncConfig {
    private final byte[] key;
    private final Alphabet alphabet;

    @UtilityClass
    public static class Param {
        public static final String ALPHABET = "alphabet";
        public static final String KEY_ID = "keyId";
        public static final String KEY = "key";
    }
}
