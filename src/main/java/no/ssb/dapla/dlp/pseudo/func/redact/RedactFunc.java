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

    public RedactFunc(@NonNull PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.config = configService.resolve(genericConfig);
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        input.getValues().forEach(in -> {
            String plain = String.valueOf(in);
            if (config.getRegex() != null) {
                output.add(plain.replaceAll(config.getRegex(), config.getPlaceholder()));
            }
            else {
                output.add(config.getPlaceholder());
            }
        });

        return output;
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        input.getValues().forEach(in -> output.add(in));

        return output;
    }

}
