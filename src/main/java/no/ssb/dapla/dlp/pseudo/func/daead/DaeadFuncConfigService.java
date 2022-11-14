package no.ssb.dapla.dlp.pseudo.func.daead;

import com.google.crypto.tink.DeterministicAead;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.daead.DaeadFuncConfig.Param.DAEAD;

@Slf4j
public class DaeadFuncConfigService {

    public DaeadFuncConfig resolve(PseudoFuncConfig cfg) {

        return DaeadFuncConfig.builder()
                .daead(cfg.getRequired(DAEAD, DeterministicAead.class))
                .build();
    }

}
