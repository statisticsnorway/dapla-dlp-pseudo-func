package no.ssb.dapla.dlp.pseudo.func.composite;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class MapAndEncryptFuncConfig {
    String encryptionFunc;
    String mapFunc;

    @UtilityClass
    public static class Param {
        public static final String ENCRYPTION_FUNC_IMPL = "encryptionFunc";
        public static final String MAP_FUNC_IMPL = "mapFunc";
    }
}
