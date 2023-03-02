package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.crypto.tink.daead.DeterministicAeadConfig;
import no.ssb.crypto.tink.fpe.FpeConfig;
import no.ssb.crypto.tink.fpe.FpeParams;
import no.ssb.crypto.tink.fpe.UnknownCharacterStrategy;
import no.ssb.dapla.dlp.pseudo.func.*;
import no.ssb.dapla.dlp.pseudo.func.tink.daead.DaeadFunc;
import no.ssb.dapla.dlp.pseudo.func.tink.daead.DaeadFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.tink.daead.DaeadWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FpeFuncTest {

    @BeforeAll
    static void initTink() throws GeneralSecurityException {
        FpeConfig.register();
    }

    @Test
    void alphanumeric_fpe_fail_shouldTransformAndRestore() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Kensentme";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                FpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
        )));
    }

    @Test
    void alphanumeric_fpe_skip_shouldTransformAndRestore() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Åge Åsnes";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                FpeFuncConfig.Param.FPE, fpeWrapper.getFpe(),
                FpeFuncConfig.Param.FPE_PARAMS, FpeParams.with().unknownCharacterStrategy(UnknownCharacterStrategy.SKIP)
        )));
    }

    private void transformAndRestore(Object originalVal,  PseudoFuncConfig config) {
        Iterable originalElements = (originalVal instanceof Iterable) ? (Iterable) originalVal : ImmutableList.of(originalVal);
        PseudoFunc func = PseudoFuncFactory.create(config);
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        System.out.println(pseudonymized.getValues());
        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValues()));
        assertThat(depseudonymized.getValues()).containsExactlyElementsOf(originalElements);
    }
}