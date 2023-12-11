package no.ssb.dapla.dlp.pseudo.func.redact;

import com.google.common.base.Strings;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

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
        PseudoFuncOutput output = new PseudoFuncOutput();
        input.getValues().forEach(in -> {
            String plain = String.valueOf(in);
            if (plain.trim().isEmpty()) {
                output.add(plain);
            }
            else if (config.getRegex() != null) {
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
        input.getValues().forEach(output::add);

        return output;
    }

}
