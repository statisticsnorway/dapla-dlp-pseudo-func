package no.ssb.dapla.dlp.pseudo.func;

import no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFunc;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFuncConfig;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PseudoFuncFactoryTest {

    private final static String KEY_ID = "01DWENC90WW9K41EN0QS2Q23X4";

    private final static List<PseudoFuncConfig> GENERIC_CONFIG = List.of(
      new PseudoFuncConfig(Map.of(
        PseudoFuncConfig.Param.FUNC_NAME, "fpe-digits",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.DIGITS,
        FpeFuncConfig.Param.KEY_ID, KEY_ID
      )),

      new PseudoFuncConfig(Map.of(
        PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric+whitespace",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
        FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4"
      )),

      new PseudoFuncConfig(Map.of(
        PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric+whitespace",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
        FpeFuncConfig.Param.KEY_ID, KEY_ID
      ))
    );

    @Test
    void exampleConfigs_create_shouldInstantiateSuccessfully() {
        GENERIC_CONFIG.forEach(config -> {
            PseudoFunc func = PseudoFuncFactory.create(config);
            assertThat(func).isNotNull();
        });
    }

    @Test
    void missingParam_create_shouldFailWithProperError() {
        PseudoFuncException e;
        e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(Map.of(
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
              FpeFuncConfig.Param.KEY_ID, KEY_ID
            ));
            PseudoFuncFactory.create(config);
        });
        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'name'");

        e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(Map.of(
              PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric+whitespace",
              FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
              FpeFuncConfig.Param.KEY_ID, KEY_ID
            ));
            PseudoFuncFactory.create(config);
        });
        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'impl'");

        e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(Map.of(
              PseudoFuncConfig.Param.FUNC_NAME, "fpe-alphanumeric+whitespace",
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.KEY_ID, KEY_ID
            ));
            PseudoFuncFactory.create(config);
        });
        assertThat(stacktraceStringOf(e)).contains("Missing pseudo func param 'alphabet'") ;
    }

    private static final String stacktraceStringOf(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}