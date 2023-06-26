package no.ssb.dapla.dlp.pseudo.func.map;

public interface Mapper {

    void init(String data);
    String map(String data);

    String restore(String mapped);

}
