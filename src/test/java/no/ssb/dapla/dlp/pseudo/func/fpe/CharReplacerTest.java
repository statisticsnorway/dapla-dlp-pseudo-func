package no.ssb.dapla.dlp.pseudo.func.fpe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharReplacerTest {

    @Test
    void replaceChars() {
        char[] allowedChars = Alphabets.fromAlphabetName("alphanumeric+?").availableCharacters();
        assertThat(CharReplacer.replace("a_b-c$d#", "?", allowedChars).getResult()).isEqualTo("a?b?c?d?");
        Assertions.assertThat(CharReplacer.replace("a_b-c$d#", "", allowedChars).getResult()).isEqualTo("abcd");
        assertThat(CharReplacer.replace("a_b-c$d#", null, allowedChars).getResult()).isEqualTo("aXbXcXdX");
        assertThat(CharReplacer.replace(null, "?", allowedChars).getResult()).isEqualTo(null);
        assertThat(CharReplacer.replace("abc", null, 'X').getResult()).isEqualTo("XXX");
    }

}