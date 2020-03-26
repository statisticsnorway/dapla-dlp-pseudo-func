package no.ssb.dapla.dlp.pseudo.func;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static no.ssb.dapla.dlp.pseudo.func.ParamMetadataTest.CharType.*;
import static org.assertj.core.api.Assertions.assertThat;

class ParamMetadataTest {

    enum CharType {
        LETTER, DIGIT, WHITESPACE, PUNCTUATION, LINEBREAK, OTHER;
    }

    private static final CharType[] NONE = new CharType[] {};
    private static final CharType[] ALL = CharType.values();

    @Test
    public void parse_strings_shouldDetectCharTypePresence() {
        assertPresent("foo", LETTER);
        assertPresent("foo BAR", LETTER, WHITESPACE);
        assertPresent("foo\tBAR.", LETTER, WHITESPACE, PUNCTUATION);
        assertPresent("foo\n,BAR", LETTER, WHITESPACE, PUNCTUATION, LINEBREAK);
        assertPresent("1", DIGIT);
        assertPresent("#1", DIGIT, OTHER);
        assertPresent("a1 ;\n@", ALL);
    }

    @Test
    public void parse_null_shouldNotFail() {
        assertPresent(null, NONE);
    }

    @Test
    public void parse_empty_shouldNotFail() {
        assertPresent("", NONE);
    }

    @Test
    public void merge_collectionOfParamMetadataObjects_shouldDescribeAllRelevantCharacterTypes() {
        ParamMetadata letters = ParamMetadata.parse("abc");
        assertPresent("abc", LETTER);
        ParamMetadata digitsAndWhitespace = ParamMetadata.parse("1 2 3");
        assertPresent("1 2 3", DIGIT, WHITESPACE);
        ParamMetadata other = ParamMetadata.parse("@$%");
        assertPresent("@$%", OTHER);

        ParamMetadata merged = ParamMetadata.merge(letters, digitsAndWhitespace, other);
        assertThat(merged.hasLetter()).isTrue();
        assertThat(merged.hasDigit()).isTrue();
        assertThat(merged.hasWhitespace()).isTrue();
        assertThat(merged.hasPunctuation()).isFalse();
        assertThat(merged.hasLinebreak()).isFalse();
        assertThat(merged.hasOther()).isTrue();
    }

    @Test
    public void shouldPrintReadableToString() {
        assertThat(ParamMetadata.parse("a,b,c").toString()).isEqualTo("letter, punctuation");
        assertThat(ParamMetadata.parse("").toString()).isEqualTo("empty");
        assertThat(ParamMetadata.parse(null).toString()).isEqualTo("empty");
    }

    /**
     * Checks if all specified CharTypes are present.
     * Non-mentioned CharTypes are strictly assumed not to be present.
     */
    private void assertPresent(String s, CharType... presentCharTypes) {
        String err = " assertion error with '" + s + "'";
        Set<CharType> presentTypes = new HashSet(Arrays.asList(presentCharTypes));
        ParamMetadata pm = ParamMetadata.parse(s);
        assertThat(pm.hasLetter()).withFailMessage(LETTER + err).isEqualTo(presentTypes.contains(LETTER));
        assertThat(pm.hasDigit()).withFailMessage(DIGIT + err).isEqualTo(presentTypes.contains(DIGIT));
        assertThat(pm.hasWhitespace()).withFailMessage(WHITESPACE + err).isEqualTo(presentTypes.contains(WHITESPACE));
        assertThat(pm.hasPunctuation()).withFailMessage(PUNCTUATION + err).isEqualTo(presentTypes.contains(PUNCTUATION));
        assertThat(pm.hasLinebreak()).withFailMessage(LINEBREAK + err).isEqualTo(presentTypes.contains(LINEBREAK));
        assertThat(pm.hasOther()).withFailMessage(OTHER + err).isEqualTo(presentTypes.contains(OTHER));
    }
}