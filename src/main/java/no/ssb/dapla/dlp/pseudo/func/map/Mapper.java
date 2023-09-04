package no.ssb.dapla.dlp.pseudo.func.map;

import java.util.Map;

public interface Mapper {

    void init(Object data);
    void setConfig(Map<String, Object> config);
    Object map(Object data);

    Object restore(Object mapped);

}
