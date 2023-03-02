package no.ssb.dapla.dlp.pseudo.func.tink.fpe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import no.ssb.crypto.tink.fpe.FpeConfig;
import no.ssb.crypto.tink.fpe.FpeParams;
import no.ssb.crypto.tink.fpe.IncompatiblePlaintextException;
import no.ssb.crypto.tink.fpe.UnknownCharacterStrategy;
import no.ssb.dapla.dlp.pseudo.func.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FpeFuncTest {

    @BeforeAll
    static void initTink() throws GeneralSecurityException {
        FpeConfig.register();
    }

    @Test
    void alphanumeric_fpe_fail_onlyAcceptedCharacters() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "StringContainingOnlyAcceptedAlphabetCharacters";
        transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                FpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
        )));
    }

    @Test
    void alphanumeric_fpe_fail_charactersExcludedFromAlphabet() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "String with spaces";
        assertThatExceptionOfType(IncompatiblePlaintextException.class).isThrownBy(() -> {

            transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                    PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                    PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                    FpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
            )));
        });
    }

    @Test
    void alphanumeric_fpe_fail_shortString() {
        // TODO Test fails: Should this case not throw an exception??
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Hi";
        assertThatExceptionOfType(IncompatiblePlaintextException.class).isThrownBy(() -> {

            transformAndRestore(originalVal, new PseudoFuncConfig(ImmutableMap.of(
                    PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                    PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                    FpeFuncConfig.Param.FPE, fpeWrapper.getFpe()
            )));
        });
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

    @Test
    void alphanumeric_fpe_skip_charactersOutsideAlphabetSkipped() {
        FpeWrapper fpeWrapper = new FpeWrapper();
        String originalVal = "Åge Åsnes";
        PseudoFunc func = PseudoFuncFactory.create(new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, String.format("tink-fpe(%s)", fpeWrapper.getKeyId()),
                PseudoFuncConfig.Param.FUNC_IMPL, FpeFunc.class.getName(),
                FpeFuncConfig.Param.FPE, fpeWrapper.getFpe(),
                FpeFuncConfig.Param.FPE_PARAMS, FpeParams.with().unknownCharacterStrategy(UnknownCharacterStrategy.SKIP)
        )));
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        String pseudonymizedText = pseudonymized.getStringValues().iterator().next();
        System.out.println(pseudonymizedText);
        assertThat(pseudonymizedText).matches(Pattern.compile("^Å.{2}\\sÅ.{4}$"));
    }

    private void transformAndRestore(Object originalVal, PseudoFuncConfig config) {
        Iterable originalElements = (originalVal instanceof Iterable) ? (Iterable) originalVal : ImmutableList.of(originalVal);
        PseudoFunc func = PseudoFuncFactory.create(config);
        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(originalVal));
        System.out.println(pseudonymized.getValues());
        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValues()));
        assertThat(depseudonymized.getValues()).containsExactlyElementsOf(originalElements);
    }
}