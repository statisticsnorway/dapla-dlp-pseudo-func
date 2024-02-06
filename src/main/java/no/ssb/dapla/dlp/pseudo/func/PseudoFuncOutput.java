package no.ssb.dapla.dlp.pseudo.func;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Value
@NoArgsConstructor
public class PseudoFuncOutput {
    List<Object> values = new ArrayList<>();
    List<String> warnings = new ArrayList<>();
    Map<String, String> metadata = new HashMap<>();


    public PseudoFuncOutput(Object v) {
        if (v instanceof Collection) {
            values.addAll((Collection) v);
        }
        else if (v.getClass().isArray()) {
            values.addAll(Arrays.asList((Object[]) v));
        }
        else {
            values.add(v);
        }
    }

    public static PseudoFuncOutput of(Object v) {
        return new PseudoFuncOutput(v);
    }

    public void add(Object object) {
        values.add(object);
    }

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    public List<String> getStringValues() {
        return values.stream()
          .map(String::valueOf)
          .collect(Collectors.toList());
    }

    public Object getFirstValue() {
        return getValues().getFirst();
    }

    @Override
    public String toString() {
        return String.valueOf(values);
    }

    public boolean hasWarnings() {
        return ! warnings.isEmpty();
    }

}
