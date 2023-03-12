package no.ssb.dapla.dlp.pseudo.func.util;

import com.google.common.collect.ImmutableMap;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.nullToEmpty;

@UtilityClass
public class FromString {

    private static Map<Class<?>, Function<String,?>> fromStringFunctionMap = ImmutableMap.<Class<?>, Function<String,?>>builder()
      .put(String.class, s -> s)
      .put(Character.class, s -> (blankToNull(s) == null) ? null : Character.valueOf(s.charAt(0)))
      .put(Long.class, s -> (blankToNull(s) == null) ? null : Long.valueOf(s))
      .put(Integer.class, s -> (blankToNull(s) == null) ? null : Integer.valueOf(s))
      .put(Double.class, s -> (blankToNull(s) == null) ? null : Double.valueOf(s))
      .put(Float.class, s -> (blankToNull(s) == null) ? null : Float.valueOf(s))
      .put(BigInteger.class, s -> (blankToNull(s) == null) ? null : new BigInteger(s))
      .put(BigDecimal.class, s -> (blankToNull(s) == null) ? null : new BigDecimal(s))
      .put(Boolean.class, s -> {
          if ("1".equals(s)) {
              return Boolean.TRUE;
          }
          else {
              return (s == null) ? null : Boolean.valueOf(s);
          }
      })
      .build();

    private static String blankToNull(String s) {
        return emptyToNull(nullToEmpty(s).trim());
    }

    private static <E extends Enum<E>> E convertEnum(String name, Class<E> clazz) {
        if (blankToNull(name) == null) {
            return null;
        }
        for (E e : clazz.getEnumConstants()) {
            if (name.equalsIgnoreCase(e.toString())) {
                return e;
            }
        }

        throw new IllegalArgumentException("No matching enum value '" + name + "' in enum " + clazz);
    }

    public static <T> T convert(String data, Class<T> clazz) {
        if (clazz.isEnum()) {
            return (T) convertEnum(data, (Class<Enum>) clazz);
        }
        else {
            return (T) fromStringFunctionMap.get(clazz).apply(data);
        }
    }

}
