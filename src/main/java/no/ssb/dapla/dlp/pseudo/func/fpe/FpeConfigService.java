package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.config.Alphabet;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;

import java.util.Base64;

public class FpeConfigService {

    public FpeFuncConfig resolve(PseudoFuncConfig genericConfig) {

        String alphabet = genericConfig.getRequired(FpeFuncConfig.Param.ALPHABET, String.class);
        String base64EncodedKey = genericConfig.getRequired(FpeFuncConfig.Param.KEY, String.class);
        Boolean isReplaceIllegalChars = genericConfig.get(FpeFuncConfig.Param.REPLACE_ILLEGAL_CHARS, Boolean.class).orElse(true);
        String illegalCharReplacement = genericConfig.get(FpeFuncConfig.Param.REPLACE_ILLEGAL_CHARS_WITH, String.class).orElse(null);

        return FpeFuncConfig.builder()
          .alphabet(resolveAlphabet(alphabet))
          .key(resolveKey(base64EncodedKey))
          .replaceIllegalChars(isReplaceIllegalChars)
          .replaceIllegalCharsWith(illegalCharReplacement)
          .build();
    }

    Alphabet resolveAlphabet(String alphabet) {
        return Alphabets.fromAlphabetName(alphabet);
    }

    byte[] resolveKey(String base64EncodedKey) {
        return Base64.getDecoder().decode(base64EncodedKey);
    }

}
