package no.ssb.dapla.dlp.pseudo.func.composite;

import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFunc;
import no.ssb.dapla.dlp.pseudo.func.fpe.FpeFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.map.MapFunc;
import no.ssb.dapla.dlp.pseudo.func.map.MappingNotFoundException;
import no.ssb.dapla.dlp.pseudo.func.map.TestMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MapAndEncryptFuncTest {
    private static final String BASE64_ENCODED_KEY = "w0/G6A5e/KHtTo31FD6mhhS1Tkga43l79IBK24gM4F8=";

    final PseudoFuncConfig config = new PseudoFuncConfig(ImmutableMap.of(
            PseudoFuncConfig.Param.FUNC_DECL, "map-fpe-test",
            PseudoFuncConfig.Param.FUNC_IMPL, MapAndEncryptFunc.class.getName(),
            MapAndEncryptFuncConfig.Param.MAP_FUNC_IMPL, MapFunc.class.getName(),
            MapAndEncryptFuncConfig.Param.ENCRYPTION_FUNC_IMPL, FpeFunc.class.getName(),
            FpeFuncConfig.Param.ALPHABET, "alphanumeric+whitespace",
            FpeFuncConfig.Param.KEY_ID, "keyId1",
            FpeFuncConfig.Param.KEY_DATA, BASE64_ENCODED_KEY

    ));
    /*
     * This test should transform OriginalValue -> MappedValue -> FPE encrypted MappedValue
     * (and back)
     */
    @Test
    public void transformAndRestore() {
        String expectedVal = "ygd1M9at1nK"; // FPE encrypted MappedValue
        PseudoFunc func = PseudoFuncFactory.create(config);

        PseudoFuncOutput pseudonymized = func.apply(PseudoFuncInput.of(TestMapper.ORIGINAL));
        assertThat(pseudonymized.getValue()).isEqualTo(expectedVal);

        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(pseudonymized.getValue()));
        assertThat(depseudonymized.getValue()).isEqualTo(TestMapper.ORIGINAL);
    }

    @Test
    public void call_apply_with_mapping_failure() {
        PseudoFunc func = PseudoFuncFactory.create(config);

        assertThrows(MappingNotFoundException.class,() ->
            func.apply(PseudoFuncInput.of("unknown"))
        );
    }

    @Test
    public void call_restore_with_mapping_failure() {
        PseudoFunc func = PseudoFuncFactory.create(config);

        assertThrows(MappingNotFoundException.class,() ->
                func.restore(PseudoFuncInput.of("unknown"))
        );
    }

}
