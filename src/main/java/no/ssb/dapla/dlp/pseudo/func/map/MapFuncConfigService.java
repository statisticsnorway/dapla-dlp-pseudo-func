package no.ssb.dapla.dlp.pseudo.func.map;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import static no.ssb.dapla.dlp.pseudo.func.map.MapFuncConfig.Param.VERSION_TIMESTAMP;

public class MapFuncConfigService {
    public MapFuncConfig resolve(PseudoFuncConfig genericConfig) {
        String context = genericConfig.getRequired(MapFuncConfig.Param.CONTEXT, String.class);

        return MapFuncConfig.builder()
                .versionTimestamp(genericConfig.get(VERSION_TIMESTAMP, String.class).orElse(null))
                .context(context)
                .build();
    }

}
