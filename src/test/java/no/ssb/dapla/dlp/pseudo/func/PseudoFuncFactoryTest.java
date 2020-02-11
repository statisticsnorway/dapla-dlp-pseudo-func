package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
    private final static String KEY_CONTENT = "8weo9VlQTuPqxjVWaHAupOdCwNpn4CFz";

    private final static List<PseudoFuncConfig> GENERIC_CONFIG = ImmutableList.of(
      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-digits(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.DIGITS,
        FpeFuncConfig.Param.KEY_ID, KEY_ID,
        FpeFuncConfig.Param.KEY, KEY_CONTENT
      )),

      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-text(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
        FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4",
        FpeFuncConfig.Param.KEY, KEY_CONTENT
      )),

      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric+whitespace(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
        FpeFuncConfig.Param.KEY_ID, KEY_ID,
        FpeFuncConfig.Param.KEY, KEY_CONTENT
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
            PseudoFuncConfig config = new PseudoFuncConfig(ImmutableMap.of(
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
              FpeFuncConfig.Param.KEY_ID, KEY_ID,
              FpeFuncConfig.Param.KEY, KEY_CONTENT
            ));
            PseudoFuncFactory.create(config);
        });
        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'decl'");

        e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(ImmutableMap.of(
              PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric(param1)",
              FpeFuncConfig.Param.ALPHABET, AlphabetType.ALPHANUMERIC_WHITESPACE,
              FpeFuncConfig.Param.KEY_ID, KEY_ID,
              FpeFuncConfig.Param.KEY, KEY_CONTENT
            ));
            PseudoFuncFactory.create(config);
        });
        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'impl'");

        e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(ImmutableMap.of(
              PseudoFuncConfig.Param.FUNC_DECL, "fpe-text",
              PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
              FpeFuncConfig.Param.KEY_ID, KEY_ID,
              FpeFuncConfig.Param.KEY, KEY_CONTENT
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