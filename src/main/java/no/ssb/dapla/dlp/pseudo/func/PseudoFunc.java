package no.ssb.dapla.dlp.pseudo.func;

public interface PseudoFunc {
    String getFuncName();

    /**
     * Pseudonymize
     */
    PseudoFuncOutput apply(PseudoFuncInput input);

    /**
     * Restore to original value (invert pseudonymization)
     */
    PseudoFuncOutput restore(PseudoFuncInput input);
}
