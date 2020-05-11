package no.ssb.dapla.dlp.pseudo.func.text;

import no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.LATIN_1_SUPPLEMENT;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.LETTERS;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.LOWERCASE;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.subset;

class UnicodeCharsTest {

    @Test
    void testStuff() {
        List<Character> chars = UnicodeChars.listOf(
          subset(LATIN_1_SUPPLEMENT).and(LOWERCASE, LETTERS),
          subset(BASIC_LATIN).and(LOWERCASE)
        );
        System.out.println("Chars found: " + chars.size());
        System.out.println(chars);
    }

    @Test
    void testStuff2() {
        char[] chars = UnicodeChars.arrayOf(BASIC_LATIN);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (! Character.isLetterOrDigit(c)) {
                sb.append(c);
            }
        }
        System.out.println(sb.toString());
    }

}