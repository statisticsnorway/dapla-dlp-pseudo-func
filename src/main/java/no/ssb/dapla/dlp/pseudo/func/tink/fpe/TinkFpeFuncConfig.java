package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.crypto.tink.fpe.FpeParams;

@Value
@Builder
public class TinkFpeFuncConfig {
    private final Fpe fpe;
    private final FpeParams fpeParams;

    @UtilityClass
    public static class Param {
        public static final String FPE = "fpe";
        public static final String KEY_ID = "keyId";
        public static final String UNKNOWN_CHARACTER_STRATEGY = "strategy";
        public static final String REDACT_CHAR = "redactChar";
        public static final String TWEAK = "tweak";
    }
}
