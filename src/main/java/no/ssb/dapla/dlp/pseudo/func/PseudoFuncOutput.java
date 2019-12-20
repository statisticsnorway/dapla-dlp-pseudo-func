package no.ssb.dapla.dlp.pseudo.func;

import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Value
@NoArgsConstructor
public class PseudoFuncOutput {
    private List<Object> values = new ArrayList<>();

    public PseudoFuncOutput(Object v) {
        if (v instanceof Collection) {
            values.addAll((Collection) v);
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
