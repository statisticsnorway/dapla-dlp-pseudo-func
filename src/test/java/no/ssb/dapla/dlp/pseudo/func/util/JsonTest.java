package no.ssb.dapla.dlp.pseudo.func.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import no.ssb.dapla.dlp.pseudo.func.util.Json.JsonException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class SimplePojo {
        private Integer someInt;
        private String someString;
        private Double someDouble;
        private boolean someBool;
        private String[] someArray;
    }

    @Test
    void simplePojo_shouldConvertToAndFromJson() throws Exception {

        SimplePojo obj = new SimplePojo(42, "blah", 42.13D, true, new String[] {"foo", "bar"});
        String json = Json.from(obj);
        JSONAssert.assertEquals(
          "{\"someArray\":[\"foo\", \"bar\"],\"someString\":\"blah\",\"someInt\":42,\"someBool\":true}",
          json, JSONCompareMode.LENIENT
        );

        SimplePojo obj2 = Json.toObject(SimplePojo.class, json);
        assertThat(obj).isEqualTo(obj2);
    }

    @Test
    void nestedMap_shouldConvertToAndFromJson() throws Exception {
        Map<String, Object> obj = Map.of(
          "someInt", 1,
          "someString", "blah",
          "someBool", true,
          "someList", List.of(1, 2, 3),
          "someMap", Map.of("one", 1)
        );

        String json = Json.from(obj);
        JSONAssert.assertEquals(
          "{\"someList\":[1,2,3],\"someString\":\"blah\",\"someInt\":1,\"someMap\":{\"one\":1},\"someBool\":true}",
          json, JSONCompareMode.LENIENT
        );

        Map<String, Object> obj2 = Json.toObject(new TypeReference<>() {}, json);
        assertThat(obj).isEqualTo(obj2);
    }

    @Test
    void arrayOfNestedMaps_shouldConvertToAndFromJson() throws Exception {
        List<Map<String,Object>> obj = List.of(
          Map.of("foo", "foo val"),
          Map.of("bar", "bar val")
        );

        String json = Json.from(obj);
        JSONAssert.assertEquals(
          "[{\"foo\": \"foo val\"}, {\"bar\": \"bar val\"}]",
          json, JSONCompareMode.LENIENT
        );

        List<Map<String, Object>> obj2 = Json.toObject(new TypeReference<>() {}, json);
        assertThat(obj).isEqualTo(obj2);
    }

    @Test
    void incompatibleObject_shouldThrowJsonException() throws Exception {
        @Value
        class Something {
            private final String blah;
        }

        JsonException e = assertThrows(JsonException.class, () -> {
            Json.toObject(Something.class, "{\"foo\": \"bar\"}");
        });
        assertThat(e.getMessage()).isEqualTo("Error mapping JSON to Something object");
    }

}