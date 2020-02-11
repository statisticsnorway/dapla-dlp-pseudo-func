package no.ssb.dapla.dlp.pseudo.func;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractPseudoFunc implements PseudoFunc {

    @NonNull @Getter
    private final String funcDecl;
}
