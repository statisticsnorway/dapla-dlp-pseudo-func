package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FpeFuncTest {

    private static final String BASE64_ENCODED_KEY = "w0/G6A5e/KHtTo31FD6mhhS1Tkga43l79IBK24gM4F8=";

    @Test
    void alphanumeric_fpe_shouldTransformAndRestore() {
        String originalVal = "Ken sent me";
        String expectedVal = "80k6tXYpÅYp";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY, BASE64_ENCODED_KEY

        )));
    }

    @Test
    void multipleAlphanumeric_fpe_shouldTransformAndRestore() {
        List originalVal = ImmutableList.of("Ken sent me...", "Kilroy was here!");
        List expectedVal = ImmutableList.of("6>\\SNXjTyhajLy", "`(JI,xQ|&mÖeA:s)");
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION,
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY, BASE64_ENCODED_KEY
        )));
    }

    @Test
    void digits_fpe_shouldTransformAndRestore() {
        String originalVal = "01010050134";
        String expectedVal = "02052091614";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-digits-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.DIGITS,
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY, BASE64_ENCODED_KEY
        )));
    }

    @Test
    void customAlphabet_fpe_shouldTransformAndRestore() {
        String originalVal = "AABBCC";
        String expectedVal = "BABHHJ";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
              PseudoFuncConfig.Param.FUNC_DECL, "fpe-custom-test",
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.ALPHABET, "ABCDEFGHIJ",
              FpeFuncConfig.Param.KEY_ID, "keyId1",
              FpeFuncConfig.Param.KEY, BASE64_ENCODED_KEY
        )));
    }

    @Test
    void longValue_fpe_shouldTransformAndRestore() {
        Long originalVal = 123456789L;
        Long expectedVal = 204992912L;
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-digits-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.DIGITS,
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY, BASE64_ENCODED_KEY
        )));
    }

    private void transformAndRestore(Object originalVal, Object expectedVal, PseudoFuncConfig config) {
        PseudoFunc func = PseudoFuncFactory.create(config);

        Iterable expectedElements = (expectedVal instanceof Iterable) ? (Iterable) expectedVal : ImmutableList.of(expectedVal);
        Iterable originalElements = (originalVal instanceof Iterable) ? (Iterable) originalVal : ImmutableList.of(originalVal);

        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        assertThat(pseudonymized.getValues()).containsExactlyElementsOf(expectedElements);

        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValues()));
        assertThat(depseudonymized.getValues()).containsExactlyElementsOf(originalElements);
    }

}