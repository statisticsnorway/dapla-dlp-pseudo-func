package no.ssb.dapla.dlp.pseudo.func.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.Map;

@UtilityClass
public class Json {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Convert JSON to Object
     */
    public static <T> T toObject(Class<T> type, String json) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        }
        catch (IOException e) {
            throw new RuntimeException("Error mapping JSON to " + type.getSimpleName() + " object", e);
        }
    }

    /**
     * Convert JSON to Object
     *
     * Use with generics, like new TypeReference<HashMap<MyPair, String>>() {}
     */
    public static <T> T toObject(TypeReference<T> type, String json) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        }
        catch (IOException e) {
            throw new RuntimeException("Error mapping JSON to " + type.getType() + " object", e);
        }
    }

    /**
     * Convert JSON to String->Object map
     */
    public static Map<String, Object> toGenericMap(String json) {
        return Json.toObject(new TypeReference<>() {}, json);
    }

    /**
     * Convert Object to JSON
     */
    public static String from(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping " +  object.getClass().getSimpleName() + " object to JSON", e);
        }
    }

    /**
     * Convert Object to pretty (indented) JSON
     */
    public static String prettyFrom(Object object) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping " +  object.getClass().getSimpleName() + " object to JSON", e);
        }
    }
}
