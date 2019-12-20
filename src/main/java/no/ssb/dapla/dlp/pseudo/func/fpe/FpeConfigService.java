package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.config.Alphabet;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.key.PseudoKey;
import no.ssb.dapla.dlp.pseudo.key.service.PseudoKeyService;
import no.ssb.dapla.dlp.pseudo.key.service.PseudoKeyServiceDummyImpl;

public class FpeConfigService {

    private final static PseudoKeyService pseudoKeyService = new PseudoKeyServiceDummyImpl();

    public FpeFuncConfig resolve(PseudoFuncConfig genericConfig) {

        String alphabet = genericConfig.getRequired(FpeFuncConfig.Param.ALPHABET, String.class);
        String keyId = genericConfig.getRequired(FpeFuncConfig.Param.KEY_ID, String.class);

        return FpeFuncConfig.builder()
          .alphabet(resolveAlphabet(alphabet))
          .key(resolveKey(keyId))
          .build();
    }

    byte[] resolveKey(String keyId) {
        PseudoKey key = pseudoKeyService.getKey(keyId);
        return key.asByteArray();
    }

    Alphabet resolveAlphabet(String alphabet) {
        return Alphabets.alphabetOf(alphabet);
    }

}
