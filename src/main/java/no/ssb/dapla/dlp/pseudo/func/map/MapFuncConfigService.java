package no.ssb.dapla.dlp.pseudo.func.map;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.map.MapFuncConfig.Param.MAP_FAILURE_STRATEGY;
import static no.ssb.dapla.dlp.pseudo.func.map.MapFuncConfig.Param.SNAPSHOT_DATE;

public class MapFuncConfigService {
    public MapFuncConfig resolve(PseudoFuncConfig genericConfig) {
        String context = genericConfig.getRequired(MapFuncConfig.Param.CONTEXT, String.class);

        return MapFuncConfig.builder()
                .snapshotDate(genericConfig.get(SNAPSHOT_DATE, String.class).orElse(null))
                .mapFailureStrategy(genericConfig.get(MAP_FAILURE_STRATEGY, MapFailureStrategy.class)
                        .orElse(MapFailureStrategy.RETURN_ORIGINAL))
                .context(context)
                .build();
    }

}
