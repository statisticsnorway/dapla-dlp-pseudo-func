package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class FpeFuncTest {

    private static final String BASE64_ENCODED_KEY = "w0/G6A5e/KHtTo31FD6mhhS1Tkga43l79IBK24gM4F8=";

    @Test
    void alphanumeric_fpe_shouldTransformAndRestore() {
        String originalVal = "Ken sent me";
        String expectedVal = "sXVnUlOJlvB";
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, "alphanumeric+whitespace",
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY_DATA, BASE64_ENCODED_KEY

        )));
    }

    @ParameterizedTest
    @CsvSource({
      "01010050134, 02052091614",
      "00, 00",
      "000, 336"
    })
    void digits_fpe_shouldTransformAndRestore(String originalVal, String expectedVal) {
        transformAndRestore(originalVal, expectedVal, new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "fpe-digits-test",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          FpeFuncConfig.Param.ALPHABET, "digits",
          FpeFuncConfig.Param.KEY_ID, "keyId1",
          FpeFuncConfig.Param.KEY_DATA, BASE64_ENCODED_KEY
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
              FpeFuncConfig.Param.KEY_DATA, BASE64_ENCODED_KEY
        )));
    }

    @Test
    void stringWithIllegalChars_fpe_shouldNotFailPseudonumization() {
        String originalVal = "ABCæøå";

        PseudoFunc func = PseudoFuncFactory.create(new PseudoFuncConfig(ImmutableMap.<String, Object>builder()
          .put(PseudoFuncConfig.Param.FUNC_DECL, "fpe-test")
          .put(PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName())
          .put(FpeFuncConfig.Param.ALPHABET, "ABCDEFGHIJ")
          .put(FpeFuncConfig.Param.KEY_ID, "keyId1")
          .put(FpeFuncConfig.Param.KEY_DATA, BASE64_ENCODED_KEY)
          .build()
        ));

        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        assertThat(pseudonymized.getValue()).isEqualTo("GGB");
    }


    private void transformAndRestore(String originalVal, String expectedVal, PseudoFuncConfig config) {
        PseudoFunc func = PseudoFuncFactory.create(config);

        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        assertThat(pseudonymized.getValue()).isEqualTo(expectedVal);

        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValue()));
        assertThat(depseudonymized.getValue()).isEqualTo(originalVal);
    }

}