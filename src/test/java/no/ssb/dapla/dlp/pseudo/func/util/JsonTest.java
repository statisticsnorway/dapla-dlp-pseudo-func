package no.ssb.dapla.dlp.pseudo.func.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonTest {

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

}