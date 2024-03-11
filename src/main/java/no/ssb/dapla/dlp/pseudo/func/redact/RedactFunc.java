package no.ssb.dapla.dlp.pseudo.func.redact;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

@Slf4j
public class RedactFunc extends AbstractPseudoFunc {
    private final RedactFuncConfigService configService = new RedactFuncConfigService();
    private final RedactFuncConfig config;

    // A.k.a Free Text Redaction
    private static final String ALGORITHM = "REDACT";

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    public RedactFunc(@NonNull PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.config = configService.resolve(genericConfig);
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        String plain = input.value();
        if (plain.trim().isEmpty()) {
            return PseudoFuncOutput.of(plain);
        } else if (config.getRegex() != null) {
            return PseudoFuncOutput.of(plain.replaceAll(config.getRegex(), config.getPlaceholder()));
        } else {
            return PseudoFuncOutput.of(config.getPlaceholder());
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        return PseudoFuncOutput.of(input.value());
    }

}
