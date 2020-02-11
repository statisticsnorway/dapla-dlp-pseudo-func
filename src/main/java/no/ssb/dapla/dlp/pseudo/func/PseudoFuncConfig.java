package no.ssb.dapla.dlp.pseudo.func;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import no.ssb.dapla.dlp.pseudo.func.util.Json;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode
public class PseudoFuncConfig implements Serializable {

    @Getter
    private final String funcDecl;

    @Getter
    private final String funcImpl;


    private final Map<String, Object> config = new HashMap<>();

    /** Construct from Map */
    public PseudoFuncConfig(Map<String, Object> params) {
        config.putAll(params);
        funcDecl = getRequired(Param.FUNC_DECL, String.class);
        funcImpl = getRequired(Param.FUNC_IMPL, String.class);
    }

    /** Construct from JSON */
    public PseudoFuncConfig(String json) {
        this(Json.toGenericMap(json));
    }

    public <T> Optional<T> get(String paramName, Class<T> clazz) {
        try {
            return Optional.ofNullable(clazz.cast(config.get(paramName)));
        }
        catch (ClassCastException e) {
            throw new PseudoFuncException("Incompatible type of pseudo func param. Expected: " + clazz, e);
        }
    }

    public <T> T getRequired(String paramName, Class<T> clazz) {
        return get(paramName, clazz)
          .orElseThrow(() -> new PseudoFuncMissingParamException(paramName));
    }

    public void add(String paramName, Object o) {
        config.put(paramName, o);
    }

    public boolean has(String paramName) {
        return config.containsKey(paramName);
    }

    @UtilityClass
    public static class Param {
        public static final String FUNC_DECL = "decl";
        public static final String FUNC_IMPL = "impl";
    }

    public static class PseudoFuncMissingParamException extends PseudoFuncException {
        public PseudoFuncMissingParamException(String paramName) {
            super("Missing pseudo func param '" + paramName + "'");
        }
    }

}
