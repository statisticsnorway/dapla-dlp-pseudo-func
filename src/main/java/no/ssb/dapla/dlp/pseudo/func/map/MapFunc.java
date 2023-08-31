package no.ssb.dapla.dlp.pseudo.func.map;

import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.util.ServiceLoader;

@Slf4j
public class MapFunc extends AbstractPseudoFunc {
    private final MapFuncConfig config;
    private final MapFuncConfigService mapFuncConfigService = new MapFuncConfigService();
    private final Mapper mapper;

    public MapFunc(PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        this.config = mapFuncConfigService.resolve(genericConfig);
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
        for (Object inputValue : input.getValues()) {
            mapper.init(String.valueOf(inputValue));
        }
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();

        for (Object inputValue : input.getValues()) {
            String plain = String.valueOf(inputValue);
            final Object pseudonymized = mapper.map(plain);
            output.add(pseudonymized);
        }

        return output;
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();

        for (Object inputValue : input.getValues()) {
            String mapped = String.valueOf(inputValue);
            final Object clear = mapper.restore(mapped);
            output.add(clear);
        }

        return output;
    }

}
