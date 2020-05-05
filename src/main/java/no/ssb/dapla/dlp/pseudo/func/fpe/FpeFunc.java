package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import no.ssb.dapla.dlp.pseudo.func.util.FromString;

import static no.ssb.dapla.dlp.pseudo.func.fpe.Domains.domainOf;

@Slf4j
public class FpeFunc extends AbstractPseudoFunc {
    private final FpeFuncConfig config;
    private final FormatPreservingEncryption fpe;
    private final FpeConfigService fpeConfigService = new FpeConfigService();

    // We should consider implementing a more sophisticated tweak, see:
    // https://crypto.stackexchange.com/questions/10903/what-are-the-uses-of-tweaks-in-block-ciphers
    private static final byte[] STATIC_TWEAK = new byte[0];

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
        PseudoFuncOutput output = new PseudoFuncOutput();

        for (Object inputValue : input.getValues()) {
            String plain = String.valueOf(inputValue);

            if (config.isReplaceIllegalChars()) {
                CharReplacer.ReplacementResult res = CharReplacer.replace(plain, config.getReplaceIllegalCharsWith(), config.getAlphabet().availableCharacters());
                if (res.hasReplacedChars()) {
                    plain = res.getResult();
                    output.addWarning(String.format("%s --> %s replaced by '%s' before pseudo",
                      getFuncDecl(), res.getReplacedChars(), res.getReplacementString()));
                }
            }
            final String pseudonymized;
            try {
                pseudonymized = fpe.encrypt(plain, STATIC_TWEAK);
            }
            catch (Exception e) {
                throw new FpePseudoFuncException("FPE pseudo apply error. func=" + getFuncDecl() + ", contentType=" + input.getParamMetadata(), e);
            }

            output.add(FromString.convert(pseudonymized, inputValue.getClass()));
        }

        return output;
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();

        for (Object inputValue : input.getValues()) {
            String pseudonymized = String.valueOf(inputValue);
            String plain = fpe.decrypt(pseudonymized, STATIC_TWEAK);
            output.add(FromString.convert(plain, inputValue.getClass()));
        }

        return output;
    }

    public static class FpePseudoFuncException extends PseudoFuncException {
        public FpePseudoFuncException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
