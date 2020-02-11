package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFunc;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PseudoFuncConfigTest {
    @Test
    void json_constructPseudoFuncConfg_shouldParseSuccessfully() {
        String json = "{\"name\":\"fpe-custom\",\"decl\":\"fpe-custom(param1)\",\"impl\":\"no.ssb.dapla.dlp.pseudo.func.FpeFunc\",\"keyId\":\"411f2af1-7588-4c7f-95e4-1c15d82ef202\",\"alphabet\":\"abcdefghij123_ \"}";
        assertThat(new PseudoFuncConfig(json)).isNotNull();
    }

    @Test
    void equals_shouldConsiderAllParams() {
        assertThat(
          new PseudoFuncConfig(ImmutableMap.of(
          PseudoFuncConfig.Param.FUNC_DECL, "foo(param1)",
          PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
          "someParam", 1
        ))).isEqualTo(
          new PseudoFuncConfig(ImmutableMap.of(
            PseudoFuncConfig.Param.FUNC_DECL, "foo(param1)",
            PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
            "someParam", 1
          ))
        );
    }

    @Test
    void equals_shouldNotBeTrueIfAnyParamsDiffer() {
        assertThat(
          new PseudoFuncConfig(ImmutableMap.of(
            PseudoFuncConfig.Param.FUNC_DECL, "foo(param1)",
            PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
            "someParam", 1
          ))).isNotEqualTo(
          new PseudoFuncConfig(ImmutableMap.of(
            PseudoFuncConfig.Param.FUNC_DECL, "foo(param1)",
            PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
            "someParam", "1"
          ))
        );
    }

}