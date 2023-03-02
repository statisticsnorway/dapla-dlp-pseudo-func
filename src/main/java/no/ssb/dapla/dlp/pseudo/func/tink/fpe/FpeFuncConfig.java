package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.crypto.tink.fpe.FpeParams;

@Value
@Builder
public class FpeFuncConfig {
    private final Fpe fpe;

    private final FpeParams fpeParams;

    @UtilityClass
    public static class Param {
        public static final String FPE = "fpe";
        public static final String FPE_PARAMS = "fpeParams";
    }
}
