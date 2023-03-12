package no.ssb.dapla.dlp.pseudo.func.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FromStringTest {

    @Test
    void stringValue_shouldConvertToInteger() {
        assertThat(FromString.convert("42", Integer.class)).isEqualTo(42);
        assertThat(FromString.convert("", Integer.class)).isNull();
        assertThat(FromString.convert("    ", Integer.class)).isNull();
        assertThat(FromString.convert(null, Integer.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToLong() {
        assertThat(FromString.convert("42", Long.class)).isEqualTo(42L);
        assertThat(FromString.convert("", Long.class)).isNull();
        assertThat(FromString.convert("    ", Long.class)).isNull();
        assertThat(FromString.convert(null, Long.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToFloat() {
        assertThat(FromString.convert("42.13", Float.class)).isEqualTo(42.13F);
        assertThat(FromString.convert("", Float.class)).isNull();
        assertThat(FromString.convert("    ", Float.class)).isNull();
        assertThat(FromString.convert(null, Float.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToDouble() {
        assertThat(FromString.convert("42.13", Double.class)).isEqualTo(42.13D);
        assertThat(FromString.convert("", Double.class)).isNull();
        assertThat(FromString.convert("    ", Double.class)).isNull();
        assertThat(FromString.convert(null, Double.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToBigInteger() {
        assertThat(FromString.convert("42", BigInteger.class)).isEqualTo(42);
        assertThat(FromString.convert("", BigInteger.class)).isNull();
        assertThat(FromString.convert("    ", BigInteger.class)).isNull();
        assertThat(FromString.convert(null, BigInteger.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToBigDecimal() {
        assertThat(FromString.convert("42.13", BigDecimal.class).doubleValue()).isEqualTo(42.13D);
        assertThat(FromString.convert("", BigDecimal.class)).isNull();
        assertThat(FromString.convert("    ", BigDecimal.class)).isNull();
        assertThat(FromString.convert(null, BigDecimal.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToString() {
        assertThat(FromString.convert("", String.class)).isEmpty();
        assertThat(FromString.convert("          ", String.class)).isEqualTo("          ");
        assertThat(FromString.convert("abc", String.class)).isEqualTo("abc");
        assertThat(FromString.convert(null, String.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToCharacter() {
        assertThat(FromString.convert("", Character.class)).isNull();
        assertThat(FromString.convert("          ", Character.class)).isNull();
        assertThat(FromString.convert("abc", Character.class)).isEqualTo('a');
        assertThat(FromString.convert(null, Character.class)).isNull();
    }

    @Test
    void stringValue_shouldConvertToEnum() {
        assertThat(FromString.convert("VAL1", SomeEnum.class)).isEqualTo(SomeEnum.VAL1);
        assertThat(FromString.convert("val1", SomeEnum.class)).isEqualTo(SomeEnum.VAL1);
        assertThat(FromString.convert("vAl2", SomeEnum.class)).isEqualTo(SomeEnum.VAL2);
        assertThat(FromString.convert("", SomeEnum.class)).isNull();
        assertThat(FromString.convert("    ", SomeEnum.class)).isNull();
        assertThat(FromString.convert(null, SomeEnum.class)).isNull();

        assertThatThrownBy(() -> {
            FromString.convert("val3", SomeEnum.class);
        })
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("No matching enum value 'val3' in enum class");
    }

    @Test
    void stringValue_shouldConvertToBoolean() {
        assertThat(FromString.convert("true", Boolean.class)).isEqualTo(Boolean.TRUE);
        assertThat(FromString.convert("True", Boolean.class)).isEqualTo(Boolean.TRUE);
        assertThat(FromString.convert("TRUE", Boolean.class)).isEqualTo(Boolean.TRUE);
        assertThat(FromString.convert("1", Boolean.class)).isEqualTo(Boolean.TRUE);
        assertThat(FromString.convert("false", Boolean.class)).isEqualTo(Boolean.FALSE);
        assertThat(FromString.convert("False", Boolean.class)).isEqualTo(Boolean.FALSE);
        assertThat(FromString.convert("FALSE", Boolean.class)).isEqualTo(Boolean.FALSE);
        assertThat(FromString.convert("0", Boolean.class)).isEqualTo(Boolean.FALSE);
        assertThat(FromString.convert("", Boolean.class)).isEqualTo(Boolean.FALSE);
        assertThat(FromString.convert(null, Boolean.class)).isNull();
    }

    private enum SomeEnum {
        VAL1, VAL2;
    }
}