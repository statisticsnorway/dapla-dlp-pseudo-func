package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.crypto.tink.DeterministicAead;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Base64;

@Slf4j
public class TinkDaeadFunc extends AbstractPseudoFunc {

    private static final byte[] DAEAD_STAMP_BYTES = "".getBytes(StandardCharsets.UTF_8);
    private final TinkDaeadFuncConfigService configService = new TinkDaeadFuncConfigService();
    private final TinkDaeadFuncConfig config;

    private static final String ALGORITHM = "TINK-DAEAD";

    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    public TinkDaeadFunc(@NonNull PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.config = configService.resolve(genericConfig);
    }

    private DeterministicAead daead() {
        return config.getDaead();
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        String plain = input.value();
        try {
            byte[] ciphertext = daead().encryptDeterministically(plain.getBytes(StandardCharsets.UTF_8), DAEAD_STAMP_BYTES);
            return PseudoFuncOutput.of(Base64.getEncoder().encodeToString(ciphertext));
        } catch (GeneralSecurityException e) {
            throw new DaeadPseudoFuncException("DAEAD apply error. func=" + getFuncDecl(), e);
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        byte[] ciphertext = Base64.getDecoder().decode(input.value());
        try {
            byte[] plaintext = daead().decryptDeterministically(ciphertext, DAEAD_STAMP_BYTES);
            return PseudoFuncOutput.of(new String(plaintext));
        } catch (GeneralSecurityException e) {
            throw new DaeadPseudoFuncException("DAEAD restore error. func=" + getFuncDecl(), e);
        }
    }

    public static class DaeadPseudoFuncException extends PseudoFuncException {
        public DaeadPseudoFuncException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
