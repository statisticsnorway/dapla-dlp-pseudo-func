package no.ssb.dapla.dlp.pseudo.func.text;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.LATIN_1_SUPPLEMENT;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.*;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.subset;
import static org.assertj.core.api.Assertions.assertThat;

class UnicodeCharsTest {

    @Test
    void testListOf() {
        List<Character> chars = UnicodeChars.listOf(
          subset(LATIN_1_SUPPLEMENT).and(LOWERCASE, LETTERS),
          subset(BASIC_LATIN).and(LOWERCASE)
        );
        assertThat(chars)
                .hasSize(61)
                .contains('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'ª', 'µ', 'º', 'ß', 'à', 'á', 'â', 'ã', 'ä',
                'å', 'æ', 'ç', 'è', 'é', 'ê', 'ë', 'ì', 'í', 'î', 'ï', 'ð', 'ñ', 'ò', 'ó', 'ô', 'õ', 'ö', 'ø', 'ù',
                'ú', 'û', 'ü', 'ý', 'þ', 'ÿ');
    }

    @Test
    void testArrayOf() {
        char[] chars = UnicodeChars.arrayOf(BASIC_LATIN);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (! Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        assertThat(sb).hasToString(" !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");

        chars = UnicodeChars.arrayOf(BASIC_LATIN, DIGITS);
        assertThat(chars).containsExactly('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    }

    @Test
    void testStringOf() {
        assertThat(UnicodeChars.stringOf(BASIC_LATIN)).isEqualTo(" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~");
        assertThat(UnicodeChars.stringOf(subset(BASIC_LATIN).and(DIGITS))).isEqualTo("0123456789");
        assertThat(UnicodeChars.stringOf(BASIC_LATIN, DIGITS)).isEqualTo("0123456789");
    }
}