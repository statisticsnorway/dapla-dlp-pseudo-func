package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncRegistry.PseudoFuncNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PseudoFuncRegistryTest {
    private static PseudoFuncRegistry registry = new PseudoFuncRegistry();

    private final static List<PseudoFuncConfig> GENERIC_CONFIG = ImmutableList.of(
      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_NAME, "dummy-1",
        PseudoFuncConfig.Param.FUNC_IMPL, DummyFunc.class.getName()
      )),
      new PseudoFuncConfig(ImmutableMap.of(
        PseudoFuncConfig.Param.FUNC_NAME, "dummy-2",
        PseudoFuncConfig.Param.FUNC_IMPL, DummyFunc.class.getName()
      ))
    );

    @BeforeAll
    static void setUp() {
        registry.init(GENERIC_CONFIG);
    }

    @Test
    void registeredFunction_get_shouldReturnFunction() {
        assertThat(registry.get("dummy-1")).isNotNull();
    }

    @Test
    void unknownFunction_get_shouldFailWithProperError() {
        PseudoFuncNotFoundException e = assertThrows(PseudoFuncNotFoundException.class, () -> {
            registry.get("nonexisting");
        });
        assertThat(e.getMessage()).isEqualTo("Unable to find pseudo function 'nonexisting'");
    }

    public static class DummyFunc extends AbstractPseudoFunc {
        public DummyFunc(PseudoFuncConfig config) {
            super(config.getFuncName());
        }

        @Override
        public PseudoFuncOutput apply(PseudoFuncInput input) {
            return new PseudoFuncOutput("pseudonymized value");
        }

        @Override
        public PseudoFuncOutput restore(PseudoFuncInput input) {
            return new PseudoFuncOutput("original value");
        }
    }
}