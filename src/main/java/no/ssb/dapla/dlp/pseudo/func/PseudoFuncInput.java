package no.ssb.dapla.dlp.pseudo.func;

public record PseudoFuncInput(String value) {
    public static PseudoFuncInput of(String value) {
        return new PseudoFuncInput(value);
    }
}
