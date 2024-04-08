package no.ssb.dapla.dlp.pseudo.func.map;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.util.Map;
import java.util.Optional;

/**
 * This class is loaded using the Java Service Provider API.
 */
public class TestMapper implements Mapper {

    public static String ORIGINAL = "OriginalValue";
    public static String MAPPED = "MappedValue";
    private MapFailureStrategy mapFailureStrategy = MapFailureStrategy.RETURN_ORIGINAL;

    @Override
    public void init(PseudoFuncInput data) {
    }

    @Override
    public void setConfig(Map<String, Object> config) {
        this.mapFailureStrategy = Optional.ofNullable(
                config.getOrDefault(MapFuncConfig.Param.MAP_FAILURE_STRATEGY, null)
        ).map(String::valueOf).map(MapFailureStrategy::valueOf).orElse(MapFailureStrategy.RETURN_ORIGINAL);
    }

    @Override
    public PseudoFuncOutput map(PseudoFuncInput input) {
        if (ORIGINAL.equals(input.value())) {
            return PseudoFuncOutput.of(MAPPED);
        } else if (mapFailureStrategy == MapFailureStrategy.RETURN_ORIGINAL) {
            return PseudoFuncOutput.of(input.value());
        } else {
            return PseudoFuncOutput.of(null);
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput mapped) {
        if (MAPPED.equals(mapped.value())) {
            return PseudoFuncOutput.of(ORIGINAL);
        } else if (mapFailureStrategy == MapFailureStrategy.RETURN_ORIGINAL) {
            return PseudoFuncOutput.of(mapped.value());
        } else {
            return PseudoFuncOutput.of(null);
        }
    }
}
