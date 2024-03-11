package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import com.google.common.collect.ImmutableMap;
import no.ssb.crypto.tink.fpe.FpeConfig;
import no.ssb.crypto.tink.fpe.IncompatiblePlaintextException;
import no.ssb.crypto.tink.fpe.UnknownCharacterStrategy;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

class TinkFpeFuncTest {

    @BeforeAll
    static void initTink() throws GeneralSecurityException {
        FpeConfig.register();
    }

    @Test
    void alphanumeric_fpe_fail_onlyAcceptedCharacters() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "StringContainingOnlyAcceptedAlphabetCharacters";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("ff31(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, TinkFpeFunc.class.getName(),
                TinkFpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
        )));
    }

    @Test
    void alphanumeric_fpe_fail_charactersExcludedFromAlphabet() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "String with spaces";
        assertThatExceptionOfType(IncompatiblePlaintextException.class).isThrownBy(() ->
            transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                    PseudoFuncConfig.Param.FUNC_DECL, String.format("ff31(%s)", fpeWrapper.getKeyId()),
                    PseudoFuncConfig.Param.FUNC_IMPL, TinkFpeFunc.class.getName(),
                    TinkFpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
            )))
        );
    }

    @Test
    @Disabled
    void alphanumeric_fpe_fail_shortString() {
        // TODO Test fails: Should this case not throw an exception??
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Hi";
        assertThatExceptionOfType(IncompatiblePlaintextException.class).isThrownBy(() ->
            transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                    PseudoFuncConfig.Param.FUNC_DECL, String.format("ff31(%s)", fpeWrapper.getKeyId()),
                    PseudoFuncConfig.Param.FUNC_IMPL, TinkFpeFunc.class.getName(),
                    TinkFpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
            )))
        );
    }

    @Test
    void alphanumeric_fpe_skip_shouldTransformAndRestore() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Åge Åsnes";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("ff31(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, TinkFpeFunc.class.getName(),
                TinkFpeFuncConfig.Param.FPE, fpeWrapper.getFpe(),
                TinkFpeFuncConfig.Param.UNKNOWN_CHARACTER_STRATEGY, UnknownCharacterStrategy.SKIP
                //FpeFuncConfig.Param.FPE_PARAMS, FpeParams.with().unknownCharacterStrategy(UnknownCharacterStrategy.SKIP)
        )));
    }

    @Test
    void alphanumeric_fpe_skip_charactersOutsideAlphabetSkipped() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Åge Åsnes";
        PseudoFunc func = PseudoFuncFactory.create(new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, TinkFpeFunc.class.getName(),
                TinkFpeFuncConfig.Param.FPE, fpeWrapper.getFpe(),
                TinkFpeFuncConfig.Param.UNKNOWN_CHARACTER_STRATEGY, UnknownCharacterStrategy.SKIP
        )));
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        String pseudonymizedText = pseudonymized.getValue();
        System.out.println(pseudonymizedText);
        assertThat(pseudonymizedText).matches(Pattern.compile("^Å.{2}\\sÅ.{4}$"));
    }

    private void transformAndRestore(String originalVal, PseudoFuncConfig config) {
        PseudoFunc func = PseudoFuncFactory.create(config);
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValue()));
        assertThat(depseudonymized.getValue()).isEqualTo(originalVal);
    }
}