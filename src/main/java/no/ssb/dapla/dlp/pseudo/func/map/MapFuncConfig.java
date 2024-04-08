package no.ssb.dapla.dlp.pseudo.func.map;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.UtilityClass;

@Value
@Builder
public class MapFuncConfig {
    private final String context;
    private final String snapshotDate;
    private final MapFailureStrategy mapFailureStrategy;

    @UtilityClass
    public static class Param {
        public static final String CONTEXT = "context";
        public static final String SNAPSHOT_DATE = "snapshotDate";
        public static final String MAP_FAILURE_STRATEGY = "failureStrategy";
    }
}
