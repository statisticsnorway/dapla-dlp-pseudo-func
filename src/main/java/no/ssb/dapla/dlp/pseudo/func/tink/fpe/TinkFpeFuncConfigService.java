package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.extern.slf4j.Slf4j;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.crypto.tink.fpe.FpeParams;
import no.ssb.crypto.tink.fpe.UnknownCharacterStrategy;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.tink.fpe.TinkFpeFuncConfig.Param.*;

@Slf4j
public class TinkFpeFuncConfigService {

    public TinkFpeFuncConfig resolve(PseudoFuncConfig cfg) {
        FpeParams fpeParams = FpeParams.DEFAULT;
        if (cfg.has(UNKNOWN_CHARACTER_STRATEGY)) {
            fpeParams.unknownCharacterStrategy(cfg.getRequired(UNKNOWN_CHARACTER_STRATEGY, UnknownCharacterStrategy.class));
        }
        if (cfg.has(REDACT_CHAR)) {
            fpeParams.redactionChar(cfg.getRequired(REDACT_CHAR, Character.class));
        }

        //UnknownCharacterStrategy strategy = cfg.get(UNKNOWN_CHARACTER_STRATEGY, UnknownCharacterStrategy.class).orElse(UnknownCharacterStrategy.FAIL);

        return TinkFpeFuncConfig.builder()
                .fpe(cfg.getRequired(FPE, Fpe.class))
                .fpeParams(fpeParams)
                .build();
    }

}
