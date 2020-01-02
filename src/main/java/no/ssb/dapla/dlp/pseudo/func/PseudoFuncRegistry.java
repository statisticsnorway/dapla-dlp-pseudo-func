package no.ssb.dapla.dlp.pseudo.func;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.util.Json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Registry that keeps track of available pseudo functions.
 */
@Slf4j
public class PseudoFuncRegistry {
    private final Map<String, PseudoFunc> funcs = new HashMap<>();

    public void init(String json) {
        try {
            List<Map<String, Object>> configMaps = Json.toObject(new TypeReference<List<Map<String, Object>>>() {}, json);
            List<PseudoFuncConfig> configs = configMaps.stream().map(PseudoFuncConfig::new).collect(Collectors.toList());
            init(configs);
        }
        catch (Exception e) {
            throw new PseudoFuncException("Error initializing pseudo functions from json config. Make sure the json format is valid.", e);
        }
    }

    public void init(Iterable<PseudoFuncConfig> configs) {
        configs.forEach(config -> {
            PseudoFunc func = PseudoFuncFactory.create(config);
            this.register(func);
        });
    }

    public void register(PseudoFunc func) {
        log.info("Registered pseudo func: " + func.getFuncName());
        funcs.put(func.getFuncName(), func);
    }

    public PseudoFunc get(String funcName) {
        return Optional.ofNullable(funcs.get(funcName))
          .orElseThrow(() -> new PseudoFuncNotFoundException(funcName)) ;
    }

    public static class PseudoFuncNotFoundException extends PseudoFuncException {
        public PseudoFuncNotFoundException(String funcName) {
            super("Unable to find pseudo function '" + funcName + "'");
        }
    }

    public Set<String> registeredFunctions() {
        return funcs.keySet();
    }
}
