package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.dapla.dlp.pseudo.func.*;

import java.security.GeneralSecurityException;

import static no.ssb.crypto.tink.fpe.util.ByteArrayUtil.b2s;
import static no.ssb.crypto.tink.fpe.util.ByteArrayUtil.s2b;

@Slf4j
public class TinkFpeFunc extends AbstractPseudoFunc {
    private final TinkFpeFuncConfig config;

    private static final String ALGORITHM = "TINK-FPE";
    @Override
    public String getAlgorithm() {
        return ALGORITHM;
    }

    public TinkFpeFunc(@NonNull PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        TinkFpeFuncConfigService configService = new TinkFpeFuncConfigService();
        this.config = configService.resolve(genericConfig);
    }

    private Fpe fpe() {
        return config.getFpe();
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        String plain = String.valueOf(input.value());
        try {
            byte[] ciphertext = fpe().encrypt(s2b(plain), config.getFpeParams());
            return PseudoFuncOutput.of(b2s(ciphertext));
        }
        catch (GeneralSecurityException e) {
            throw new TinkFpePseudoFuncException("Tink FPE apply error. func=" + getFuncDecl(), e);
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        byte[] ciphertext = s2b(String.valueOf(input.value()));
        try {
            byte[] plaintext = fpe().decrypt(ciphertext, config.getFpeParams());
            return PseudoFuncOutput.of(b2s(plaintext));
        } catch (GeneralSecurityException e) {
            throw new TinkFpePseudoFuncException("Tink FPE restore error. func=" + getFuncDecl(), e);
        }
    }

    public static class TinkFpePseudoFuncException extends PseudoFuncException {
        public TinkFpePseudoFuncException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
