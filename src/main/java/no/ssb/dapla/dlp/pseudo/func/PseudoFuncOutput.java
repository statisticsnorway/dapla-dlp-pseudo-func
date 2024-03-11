package no.ssb.dapla.dlp.pseudo.func;

import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
public class PseudoFuncOutput {
    String value;
    List<String> warnings = new ArrayList<>();
    Map<String, String> metadata = new HashMap<>();

    public PseudoFuncOutput(String value) {
        this.value = value;
    }

    public static PseudoFuncOutput of(String value) {
        return new PseudoFuncOutput(value);
    }

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean hasWarnings() {
        return ! warnings.isEmpty();
    }

}
