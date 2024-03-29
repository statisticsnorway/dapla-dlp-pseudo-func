package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFunc;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.text.CharacterGroup;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PseudoFuncFactoryTest {

    private final static String KEY_ID = "01DWENC90WW9K41EN0QS2Q23X4";
    private final static String KEY_MATERIAL = "8weo9VlQTuPqxjVWaHAupOdCwNpn4CFz";

    private final static List<PseudoFuncConfig> GENERIC_CONFIG = ImmutableList.of(
      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-digits(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, "digits",
        FpeFuncConfig.Param.KEY_ID, KEY_ID,
        FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
      )),

      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-text(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, "alphanumeric+whitespace",
        FpeFuncConfig.Param.KEY_ID, "01DWENC90WW9K41EN0QS2Q23X4",
        FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
      )),

      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric+whitespace(param1)",
        PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
        FpeFuncConfig.Param.ALPHABET, "alphanumeric+whitespace",
        FpeFuncConfig.Param.KEY_ID, KEY_ID,
        FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
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
    void missingDeclParam_create_shouldFailWithProperError() {
         final Map<String, Object> params = ImmutableMap.of(
                 PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                 FpeFuncConfig.Param.ALPHABET, CharacterGroup.ALPHANUMERIC.getChars(),
                 FpeFuncConfig.Param.KEY_ID, KEY_ID,
                 FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
         );
         PseudoFuncException e = assertThrows(PseudoFuncException.class, () -> {
             PseudoFuncConfig config = new PseudoFuncConfig(params);
             PseudoFuncFactory.create(config);
        });

        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'decl'");
    }

    @Test
    void missingImplParam_create_shouldFailWithProperError() {
        final Map<String, Object> params = ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, "fpe-alphanumeric(param1)",
                FpeFuncConfig.Param.ALPHABET, CharacterGroup.ALPHANUMERIC.getChars(),
                FpeFuncConfig.Param.KEY_ID, KEY_ID,
                FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
        );
        PseudoFuncException e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(params);
            PseudoFuncFactory.create(config);
        });

        assertThat(e.getMessage()).isEqualTo("Missing pseudo func param 'impl'");
    }

    @Test
    void missingAlphabetParam_create_shouldFailWithProperError() {
        final Map<String, Object> params = ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, "fpe-text",
                PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                FpeFuncConfig.Param.KEY_ID, KEY_ID,
                FpeFuncConfig.Param.KEY_DATA, KEY_MATERIAL
        );
        PseudoFuncException e = assertThrows(PseudoFuncException.class, () -> {
            PseudoFuncConfig config = new PseudoFuncConfig(params);
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