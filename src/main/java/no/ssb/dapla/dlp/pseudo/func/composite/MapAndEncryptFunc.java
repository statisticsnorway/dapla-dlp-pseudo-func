package no.ssb.dapla.dlp.pseudo.func.composite;

import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncFactory;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;
import no.ssb.dapla.dlp.pseudo.func.TransformDirection;

import java.util.function.Function;

import static no.ssb.dapla.dlp.pseudo.func.composite.MapAndEncryptFuncConfig.Param.*;

/**
 * This is a composite {@code PseudoFunc} that combines two {@code PseudoFunc}s into a single operation.
 * <p>
 * It assumes that one is a mapping function (e.g. MapFunc) and the other is an encryption function
 * (e.g FpeFunc, TinkFpeFunc, TinkDaeadFunc).
 *
 * The {@code MapAndEncryptFunc} must be configured with a
 *    {@link no.ssb.dapla.dlp.pseudo.func.composite.MapAndEncryptFuncConfig.Param#ENCRYPTION_FUNC_IMPL} and a
 *    {@link no.ssb.dapla.dlp.pseudo.func.composite.MapAndEncryptFuncConfig.Param#MAP_FUNC_IMPL}.
 *
 *  The {@code PseudoFuncConfig} must also contain all the necessary configs for each of the underlying
 *  {@code PseudoFunc}s. For example MapFuncConfig and TinkFpeFuncConfig.
 */
public class MapAndEncryptFunc extends AbstractPseudoFunc {

    final PseudoFunc encryptionFunc;
    final PseudoFunc mapFunc;

    public MapAndEncryptFunc(PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncDecl());
        genericConfig.add(PseudoFuncConfig.Param.FUNC_IMPL,
                genericConfig.getRequired(ENCRYPTION_FUNC_IMPL, String.class));
        var encryptionFuncConfig = genericConfig.asMap();
        genericConfig.add(PseudoFuncConfig.Param.FUNC_IMPL,
                genericConfig.getRequired(MAP_FUNC_IMPL, String.class));
        var mapFuncConfig = genericConfig.asMap();

        this.encryptionFunc = PseudoFuncFactory.create(new PseudoFuncConfig(encryptionFuncConfig));
        this.mapFunc = PseudoFuncFactory.create(new PseudoFuncConfig(mapFuncConfig));
    }

    @Override
    public String getAlgorithm() {
        return encryptionFunc.getAlgorithm();
    }

    @Override
    public void init(PseudoFuncInput input, TransformDirection direction) {
        if (direction == TransformDirection.APPLY) {
            // Prepare map from original value to mapped value
            mapFunc.init(input, direction);
        } else {
            // Decrypt and then prepare to map back to original value
            mapFunc.init(PseudoFuncInput.of(
                    encryptionFunc.restore(input).getValue()),
                    direction
            );
        }
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        // Map original value to mapped value and then encrypt
        return transform(input, mapFunc::apply, encryptionFunc::apply);
    }
    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        // Decrypt and then map back to original value
        return transform(input, encryptionFunc::restore, mapFunc::restore);
    }

    /**
     * Apply both functions {@code inner} and {@code outer} and merge both
     * outputs.
     *
     * @param input the original value
     * @param inner the inner function to apply
     * @param outer the outer function to apply
     * @return the result object
     */
    private PseudoFuncOutput transform(PseudoFuncInput input,
                                       Function<PseudoFuncInput, PseudoFuncOutput> inner,
                                       Function<PseudoFuncInput, PseudoFuncOutput> outer) {
        final PseudoFuncOutput innerOutput = inner.apply(input);
        final PseudoFuncOutput outerOutput = outer.apply(
                PseudoFuncInput.of(innerOutput.getValue()));
        innerOutput.getWarnings().forEach(outerOutput::addWarning);
        innerOutput.getMetadata().forEach(outerOutput::addMetadata);
        return outerOutput;

    }
}
