package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import no.ssb.crypto.tink.fpe.Fpe;
import no.ssb.dapla.dlp.pseudo.func.*;

import java.security.GeneralSecurityException;

import static no.ssb.crypto.tink.fpe.util.ByteArrayUtil.b2s;
import static no.ssb.crypto.tink.fpe.util.ByteArrayUtil.s2b;

@Slf4j
public class FpeFunc extends AbstractPseudoFunc {
    private final FpeFuncConfigService configService = new FpeFuncConfigService();
    private final FpeFuncConfig config;

    public FpeFunc(@NonNull PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.config = configService.resolve(genericConfig);
    }

    private Fpe fpe() {
        return config.getFpe();
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        input.getValues().forEach(in -> {
            String plain = String.valueOf(in);
            try {
                byte[] ciphertext = fpe().encrypt(s2b(plain), config.getFpeParams());
                output.add(b2s(ciphertext));
            }
            catch (GeneralSecurityException e) {
                throw new FpePseudoFuncException("Tink FPE apply error. func=" + getFuncDecl() + ", contentType=" + input.getParamMetadata(), e);
            }
        });

        return output;
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        input.getValues().forEach(in -> {
                    byte[] ciphertext = s2b(String.valueOf(in));
                    try {
                        byte[] plaintext = fpe().decrypt(ciphertext, config.getFpeParams());
                        //output.add(FromString.convert(new String(plaintext), in.getClass()));
                        output.add(b2s(plaintext));
                    }
                    catch (GeneralSecurityException e) {
                        throw new FpePseudoFuncException("Tink FPE restore error. func=" + getFuncDecl() + ", contentType=" + input.getParamMetadata(), e);
                    }
        });

        return output;
    }

    public static class FpePseudoFuncException extends PseudoFuncException {
        public FpePseudoFuncException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
