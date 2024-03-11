package no.ssb.dapla.dlp.pseudo.func.tink.daead;

import com.google.common.collect.ImmutableMap;
import com.google.crypto.tink.daead.DeterministicAeadConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;

import static org.assertj.core.api.Assertions.*;

class TinkDaeadFuncTest {

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
                PseudoFuncConfig.Param.FUNC_IMPL, TinkDaeadFunc.class.getName(),
                TinkDaeadFuncConfig.Param.DAEAD, daeadWrapper.getDaead()
        )));
    }

    private void transformAndRestore(String originalVal, PseudoFuncConfig config) {
        PseudoFunc func = PseudoFuncFactory.create(config);
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValue()));
        assertThat(depseudonymized.getValue()).isEqualTo(originalVal);
    }

}