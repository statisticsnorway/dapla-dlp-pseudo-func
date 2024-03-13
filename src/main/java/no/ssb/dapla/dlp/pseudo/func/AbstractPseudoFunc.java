package no.ssb.dapla.dlp.pseudo.func;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPseudoFunc implements PseudoFunc {

    @NonNull @Getter
    private final String funcDecl;

    @Override
    public void init(PseudoFuncInput input, TransformDirection direction) {
        // Do nothing, can be overridden by subclasses
    }
}
