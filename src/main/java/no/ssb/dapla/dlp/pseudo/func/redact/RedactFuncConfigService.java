package no.ssb.dapla.dlp.pseudo.func.redact;

import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import static no.ssb.dapla.dlp.pseudo.func.redact.RedactFuncConfig.Param.PLACEHOLDER;
import static no.ssb.dapla.dlp.pseudo.func.redact.RedactFuncConfig.Param.REGEX;

@Slf4j
public class RedactFuncConfigService {

    public RedactFuncConfig resolve(PseudoFuncConfig cfg) {
        return RedactFuncConfig.builder()
                .placeholder(cfg.get(PLACEHOLDER, String.class).orElse("***"))
                .regex(cfg.get(REGEX, String.class).orElse(null))
                .build();
    }

}
