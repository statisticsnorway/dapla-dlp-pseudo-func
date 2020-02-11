package no.ssb.dapla.dlp.pseudo.func;

public interface PseudoFunc {

    /** Name of the function declaration (including any parameters), e.g. foo(param1,param2) */
    String getFuncDecl();

    /**
     * Pseudonymize
     */
    PseudoFuncOutput apply(PseudoFuncInput input);

    /**
     * Restore to original value (invert pseudonymization)
     */
    PseudoFuncOutput restore(PseudoFuncInput input);

}
