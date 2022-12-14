package no.ssb.dapla.dlp.pseudo.func.redact;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class RedactFuncConfig {
    private final String placeholder;
    private final String regex;

    @UtilityClass
    public static class Param {
        public static final String PLACEHOLDER = "placeholder";
        public static final String REGEX = "regex";
    }
}
