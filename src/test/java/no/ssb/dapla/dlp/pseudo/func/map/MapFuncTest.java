package no.ssb.dapla.dlp.pseudo.func.map;

import com.google.common.collect.ImmutableMap;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MapFuncTest {
    @Test
    public void transformAndRestore() {
        final PseudoFuncConfig config = new PseudoFuncConfig(ImmutableMap.of(
                PseudoFuncConfig.Param.FUNC_DECL, "map-test",
                PseudoFuncConfig.Param.FUNC_IMPL, MapFunc.class.getName()
        ));
        PseudoFunc func = PseudoFuncFactory.create(config);

        PseudoFuncOutput mapOutput = func.apply(PseudoFuncInput.of(TestMapper.ORIGINAL));
        assertThat(mapOutput.getValue()).isEqualTo(TestMapper.MAPPED);

        PseudoFuncOutput depseudonymized = func.restore(PseudoFuncInput.of(mapOutput.getValue()));
        assertThat(depseudonymized.getValue()).isEqualTo(TestMapper.ORIGINAL);
    }

}
