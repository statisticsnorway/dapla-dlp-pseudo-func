package no.ssb.dapla.dlp.pseudo.func.text;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.LATIN_1_SUPPLEMENT;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.*;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.*;
import static org.assertj.core.api.Assertions.assertThat;

class UnicodeCharsTest {

    @Test
    void testListOf() {
        List<Character> chars = listOf(
          subset(LATIN_1_SUPPLEMENT).and(LOWERCASE, LETTERS),
          subset(BASIC_LATIN).and(LOWERCASE)
        );
        assertThat(chars)
                .hasSize(61)
                .containsExactly('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ª', 'µ', 'º', 'ß', 'à', 'á', 'â', 'ã', 'ä',
                'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', 'ø', 'ù',
                'ú', 'û', 'ü', 'ý', 'þ', 'ÿ');

        assertThat(str(listOf(BASIC_LATIN, Collections.emptySet())))
                .isEqualTo(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
    }

    @Test
    void testArrayOf() {
        char[] chars = arrayOf(BASIC_LATIN);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (! Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        assertThat(sb).hasToString(" !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

        assertThat(arrayOf(BASIC_LATIN, DIGITS)).containsExactly('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

        assertThat(arrayOf(subset(BASIC_LATIN).and(UPPERCASE))).containsExactly('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');

        assertThat(arrayOf(subset(BASIC_LATIN).and(WHITESPACE))).containsExactly(' ');
        assertThat(arrayOf(subset(BASIC_LATIN).and(SPACE))).containsExactly(' ');
        assertThat(arrayOf(subset(BASIC_LATIN).and(CONTROL))).isEmpty();
        assertThat(arrayOf(subset(BASIC_LATIN).and(ALPHANUMERIC).and(DIGITS))).containsExactly('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        assertThat(str(arrayOf(subset(BASIC_LATIN).and(SYMBOLS)))).isEqualTo("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
        assertThat(str(arrayOf(subset(BASIC_LATIN).and(ALPHANUMERIC)))).isEqualTo("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        assertThat(str(arrayOf(subset(BASIC_LATIN).and(ALPHANUMERIC)))).isEqualTo("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    @Test
    void testStringOf() {
        assertThat(UnicodeChars.stringOf(BASIC_LATIN)).isEqualTo(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
        assertThat(UnicodeChars.stringOf(subset(BASIC_LATIN).and(DIGITS))).isEqualTo("0123456789");
        assertThat(UnicodeChars.stringOf(BASIC_LATIN, DIGITS)).isEqualTo("0123456789");
    }

    private static String str(Collection<Character> coll) {
        return coll.stream().map(String::valueOf).collect(Collectors.joining());
    }

    private static String str(char[] chars) {
        return String.valueOf(chars);
    }

}