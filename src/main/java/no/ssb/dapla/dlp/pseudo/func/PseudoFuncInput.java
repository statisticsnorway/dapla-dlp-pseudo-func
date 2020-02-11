package no.ssb.dapla.dlp.pseudo.func;

import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class PseudoFuncInput {
    private final List<Object> values = new ArrayList<>();

    public PseudoFuncInput(Object v) {
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

    public static PseudoFuncInput of(Object v) {
        return new PseudoFuncInput(v);
    }

    public List<String> getStringValues() {
        return values.stream()
          .map(String::valueOf)
          .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.valueOf(values);
    }
}
