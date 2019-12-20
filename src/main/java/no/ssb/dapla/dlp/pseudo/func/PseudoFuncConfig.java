package no.ssb.dapla.dlp.pseudo.func;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import no.ssb.dapla.dlp.pseudo.func.util.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PseudoFuncConfig {

    @Getter
    private final String funcName;

    @Getter
    private final String funcImpl;

    private final Map<String, Object> config = new HashMap<>();

    /** Construct from map */
    public PseudoFuncConfig(Map<String, Object> params) {
        config.putAll(params);
        funcName = getRequired(Param.FUNC_NAME, String.class);
        funcImpl = getRequired(Param.FUNC_IMPL, String.class);
    }

    /** Construct from json */
    public PseudoFuncConfig(String json) {
        this(Json.toGenericMap(json));
    }

    public boolean isDefined(String paramName) {
        return config.containsKey(paramName);
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

    @UtilityClass
    public static class Param {
        public static final String FUNC_NAME = "name";
        public static final String FUNC_IMPL = "impl";
    }

    public static class PseudoFuncMissingParamException extends PseudoFuncException {
        public PseudoFuncMissingParamException(String paramName) {
            super("Missing pseudo func param '" + paramName + "'");
        }
    }

}
