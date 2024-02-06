package no.ssb.dapla.dlp.pseudo.func.map;

import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.util.ServiceLoader;

@Slf4j
public class MapFunc extends AbstractPseudoFunc {
    private final Mapper mapper;

    @Override
    public String getAlgorithm() {
        return null;
    }

    public MapFunc(PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.mapper = loadMapper();
        this.mapper.setConfig(genericConfig.asMap());
    }

    public static Mapper loadMapper() {
        // TODO: Filter Service Implementation by some annotation (to choose the implementation that is used)
        return ServiceLoader.load(Mapper.class)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(MapFunc.class.getSimpleName() + " requires a " +
                        Mapper.class.getName() + " implementation to be present on the classpath"));
    }

    @Override
    public void init(PseudoFuncInput input) {
        mapper.init(input);
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        return mapper.map(input);
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        return mapper.restore(input);
    }

}
