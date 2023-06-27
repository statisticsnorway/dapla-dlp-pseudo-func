package no.ssb.dapla.dlp.pseudo.func.map;

public interface Mapper {

    void init(Object data);
    Object map(Object data);

    Object restore(String Object);

}
