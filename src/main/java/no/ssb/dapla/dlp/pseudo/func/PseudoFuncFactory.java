package no.ssb.dapla.dlp.pseudo.func;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Constructor;

@UtilityClass
public class PseudoFuncFactory {
    public static <T extends PseudoFuncConfig> PseudoFunc create(T config) {
        try {
            Class<PseudoFunc> c = (Class<PseudoFunc>) Class.forName(config.getFuncImpl());
            Constructor<PseudoFunc> ctr = c.getConstructor(PseudoFuncConfig.class);
            return ctr.newInstance(config);
        }
        catch (Exception e) {
            throw new PseudoFuncInitException("Error instantiating pseudo function '" + config.getFuncDecl() + "'", e);
        }
    }

    public static class PseudoFuncInitException extends PseudoFuncException {
        public PseudoFuncInitException(String message, Exception e) {
            super(message, e);
        }
    }
}
