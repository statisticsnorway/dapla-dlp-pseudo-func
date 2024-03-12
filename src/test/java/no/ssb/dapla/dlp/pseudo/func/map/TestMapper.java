package no.ssb.dapla.dlp.pseudo.func.map;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.util.Map;

/**
 * This class is loaded using the Java Service Provider API.
 */
public class TestMapper implements Mapper {

    public static String ORIGINAL = "OriginalValue";
    public static String MAPPED = "MappedValue";

    @Override
    public void init(PseudoFuncInput data) {
    }

    @Override
    public void setConfig(Map<String, Object> config) {
    }

    @Override
    public PseudoFuncOutput map(PseudoFuncInput input) throws MappingNotFoundException {
        if (ORIGINAL.equals(input.value())) {
            return PseudoFuncOutput.of(MAPPED);
        } else {
            throw new MappingNotFoundException(String.format("Could not map value %s", input.value()));
        }
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput mapped) throws MappingNotFoundException {
        if (MAPPED.equals(mapped.value())) {
            return PseudoFuncOutput.of(ORIGINAL);
        } else {
            throw new MappingNotFoundException(String.format("Could not map value %s", mapped.value()));
        }
    }
}
