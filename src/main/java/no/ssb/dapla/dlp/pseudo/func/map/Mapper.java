package no.ssb.dapla.dlp.pseudo.func.map;

public interface Mapper {

    Object map(Object data);

    Object restore(Object mapped);

}
