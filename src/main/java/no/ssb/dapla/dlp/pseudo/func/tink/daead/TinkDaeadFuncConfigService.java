package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.tink.daead.TinkDaeadFuncConfig.Param.DAEAD;

@Slf4j
public class TinkDaeadFuncConfigService {

    public TinkDaeadFuncConfig resolve(PseudoFuncConfig cfg) {

        return TinkDaeadFuncConfig.builder()
                .daead(cfg.getRequired(DAEAD, DeterministicAead.class))
                .build();
    }

}
