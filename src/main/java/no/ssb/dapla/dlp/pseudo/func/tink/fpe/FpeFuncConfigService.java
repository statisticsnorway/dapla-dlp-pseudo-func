package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.extern.slf4j.Slf4j;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.crypto.tink.fpe.FpeParams;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.tink.fpe.FpeFuncConfig.Param.FPE;
import static no.ssb.dapla.dlp.pseudo.func.tink.fpe.FpeFuncConfig.Param.FPE_PARAMS;

@Slf4j
public class FpeFuncConfigService {

    public FpeFuncConfig resolve(PseudoFuncConfig cfg) {

        return FpeFuncConfig.builder()
                .fpe(cfg.getRequired(FPE, Fpe.class))
                .fpeParams(cfg.get(FPE_PARAMS, FpeParams.class).orElse(FpeParams.DEFAULT))
                .build();
    }

}
