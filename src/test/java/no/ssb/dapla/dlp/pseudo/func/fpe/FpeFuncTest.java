package no.ssb.dapla.dlp.pseudo.func.fpe;

import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FpeFuncTest {

    @Test
    void alphanumeric_fpe_shouldTransformAndRestore() {
        String originalVal = "Ken sent me";
        String expectedVal = "2y RãzFwxQM";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(Map.of(
          PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
          FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4"
        )));
    }

    @Test
    void multipleAlphanumeric_fpe_shouldTransformAndRestore() {
        List originalVal = List.of("Ken sent me...", "Kilroy was here!");
        List expectedVal = List.of("{IX$};rözS$3??", ",dj0Ãl(]|0=t3%Z<");
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(Map.of(
          PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION,
          FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4"
        )));
    }

    @Test
    void digits_fpe_shouldTransformAndRestore() {
        String originalVal = "01010050134";
        String expectedVal = "08226599165";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(Map.of(
          PseudoFuncConfig.Param.FUNC_NAME, "fpe-digits-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, AlphabetType.DIGITS,
          FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4"
        )));
    }

    @Test
    void customAlphabet_fpe_shouldTransformAndRestore() {
        String originalVal = "AABBCC";
        String expectedVal = "CAAHJE";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(Map.of(
              PseudoFuncConfig.Param.FUNC_NAME, "fpe-custom-test",
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.ALPHABET, "ABCDEFGHIJ",
              FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4"
          )));
    }

    private void transformAndRestore(Object originalVal, Object expectedVal, PseudoFuncConfig config) {
        PseudoFunc func = PseudoFuncFactory.create(config);

        Iterable expectedElements = (expectedVal instanceof Iterable) ? (Iterable) expectedVal : List.of(expectedVal);
        Iterable originalElements = (originalVal instanceof Iterable) ? (Iterable) originalVal : List.of(originalVal);


        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        assertThat(pseudonymized.getValues()).containsExactlyElementsOf(expectedElements);

        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValues()));
        assertThat(depseudonymized.getValues()).containsExactlyElementsOf(originalElements);
    }

}