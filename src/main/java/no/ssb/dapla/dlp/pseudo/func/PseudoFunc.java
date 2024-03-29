package no.ssb.dapla.dlp.pseudo.func;

public interface PseudoFunc {

    /** Name of the function declaration (including any parameters), e.g. foo(param1,param2) */
    String getFuncDecl();

    /** Name of the encryption algorithm, e.g. TINK_FPE, TINK-DAEAD */
    String getAlgorithm();

    /**
     * Preprocessing of input. This will be called before apply or restore
     */
    void init(PseudoFuncInput input, TransformDirection direction);
    /**
     * Pseudonymize
     */
    PseudoFuncOutput apply(PseudoFuncInput input);

    /**
     * Restore to original value (invert pseudonymization)
     */
    PseudoFuncOutput restore(PseudoFuncInput input);

}
