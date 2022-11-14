package no.ssb.dapla.dlp.pseudo.func.map;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

public class MapFuncConfigService {

    public MapFuncConfig resolve(PseudoFuncConfig genericConfig) {

        String context = genericConfig.getRequired(MapFuncConfig.Param.CONTEXT, String.class);

        return MapFuncConfig.builder()
                .context(context)
                .build();
    }

}
