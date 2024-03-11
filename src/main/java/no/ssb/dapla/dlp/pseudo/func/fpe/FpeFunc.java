package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.*;

import java.util.ArrayList;
import java.util.List;

import static no.ssb.dapla.dlp.pseudo.func.fpe.Domains.domainOf;

@Slf4j
public class FpeFunc extends AbstractPseudoFunc {
    private final FpeFuncConfig config;
    private final FormatPreservingEncryption fpe;
    private final FpeConfigService fpeConfigService = new FpeConfigService();

    private static final String ALGORITHM = "FPE";

    // We should consider implementing a more sophisticated tweak, see:
    // https://crypto.stackexchange.com/questions/10903/what-are-the-uses-of-tweaks-in-block-ciphers
    private static final byte[] STATIC_TWEAK = new byte[0];

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    public FpeFunc(PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        config = fpeConfigService.resolve(genericConfig);

        fpe = FormatPreservingEncryptionBuilder
                .ff1Implementation()
                .withDomain(domainOf(config.getAlphabet()))
                .withDefaultPseudoRandomFunction(config.getKey())
                .withDefaultLengthRange()
                .build();
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {

        String plain = input.value();
        List<String> warnings = new ArrayList<>();
        if (config.isReplaceIllegalChars()) {
            CharReplacer.ReplacementResult res = CharReplacer.replace(plain, config.getReplaceIllegalCharsWith(), config.getAlphabet().availableCharacters());
            if (res.hasReplacedChars()) {
                plain = res.getResult();
                warnings.add(String.format("%s --> %s replaced by '%s' before pseudo",
                        getFuncDecl(), res.getReplacedChars(), res.getReplacementString()));
            }
        }
        try {
            final String pseudonymized = isFpeTransformCompliant(plain) ? fpe.encrypt(plain, STATIC_TWEAK) : plain;
            PseudoFuncOutput output = PseudoFuncOutput.of(pseudonymized);
            warnings.forEach(output::addWarning);
            return output;
        } catch (Exception e) {
            throw new FpePseudoFuncException("FPE pseudo apply error. func=" + getFuncDecl(), e);
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
            String pseudonymized = input.value();
            String plain = isFpeTransformCompliant(pseudonymized)
                    ? fpe.decrypt(pseudonymized, STATIC_TWEAK)
                    : pseudonymized;
        return PseudoFuncOutput.of(plain);
    }

    /**
     * FPE functions require input data to be compliant. E.g. non-null and
     * minimum length of 2
     *
     * @param s the value to check
     * @return true iff a string can be transformed by the FPE function
     */
    private static boolean isFpeTransformCompliant(String s) {
        return s != null && s.length() > 2;
    }

    public static class FpePseudoFuncException extends PseudoFuncException {
        public FpePseudoFuncException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
