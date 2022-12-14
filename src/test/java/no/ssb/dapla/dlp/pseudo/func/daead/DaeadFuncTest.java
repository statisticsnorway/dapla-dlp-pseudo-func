package no.ssb.dapla.dlp.pseudo.func.daead;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.crypto.tink.daead.DeterministicAeadConfig;
import no.ssb.dapla.dlp.pseudo.func.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DaeadFuncTest {

    @BeforeAll
    static void initTink() throws GeneralSecurityException {
        DeterministicAeadConfig.register();
    }

    @Test
    void alphanumeric_daead_shouldTransformAndRestore() {
        DaeadWrapper daeadWrapper = new DaeadWrapper();
        String originalVal = "Ken sent me";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-daead(%s)", daeadWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, DaeadFunc.class.getName(),
                DaeadFuncConfig.Param.DAEAD, daeadWrapper.getDaead()
        )));
    }

    @Test
    void multipleAlphanumeric_daead_shouldTransformAndRestore() {
        DaeadWrapper daeadWrapper = new DaeadWrapper();
        List originalVal = ImmutableList.of("Ken sent me...", "Kilroy was here!");
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-daead(%s)", daeadWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, DaeadFunc.class.getName(),
                DaeadFuncConfig.Param.DAEAD, daeadWrapper.getDaead()
        )));
    }

    /*
    TODO: Implement a typesafe daead func (e.g. by storing type metadata in the ciphertext?)
    @Test
    void longValue_fpe_shouldTransformAndRestore() {
        DaeadWrapper daeadWrapper = new DaeadWrapper();
        Long originalVal = 123456789L;
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-daead(%s)", daeadWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, DaeadFunc.class.getName(),
                DaeadFuncConfig.Param.DAEAD, daeadWrapper.getDaead()
        )));
    }
    */

    private void transformAndRestore(Object originalVal,  PseudoFuncConfig config) {
        Iterable originalElements = (originalVal instanceof Iterable) ? (Iterable) originalVal : ImmutableList.of(originalVal);
        PseudoFunc func = PseudoFuncFactory.create(config);
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValues()));
        assertThat(depseudonymized.getValues()).containsExactlyElementsOf(originalElements);
    }

}