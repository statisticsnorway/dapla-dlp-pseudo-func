package no.ssb.dapla.dlp.pseudo.func.map;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import java.util.Map;

public interface Mapper {

    void init(PseudoFuncInput data);
    void setConfig(Map<String, Object> config);
    PseudoFuncOutput map(PseudoFuncInput data);

    PseudoFuncOutput restore(PseudoFuncInput mapped);

}
