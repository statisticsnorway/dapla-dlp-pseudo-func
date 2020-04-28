package no.ssb.dapla.dlp.pseudo.func.fpe;

import no.ssb.dapla.dlp.pseudo.func.CharacterGroup;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CharacterGroupTest {
    @Test
    void constants_shouldContainExpectedValues() {
        assertThat(CharacterGroup.DIGITS.getChars()).isEqualTo("0123456789");
        assertThat(CharacterGroup.WHITESPACE.getChars()).isEqualTo(" ");
        assertThat(CharacterGroup.LETTERS_LOWERCASE.getChars()).isEqualTo("abcdefghijklmnopqrstuvwxyz");
        assertThat(CharacterGroup.LETTERS_UPPERCASE.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        assertThat(CharacterGroup.LETTERS.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        assertThat(CharacterGroup.LETTERS_NO.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØabcdefghijklmnopqrstuvwxyzåæø");
        assertThat(CharacterGroup.ALPHANUMERIC.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        assertThat(CharacterGroup.ALPHANUMERIC_NO.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØabcdefghijklmnopqrstuvwxyzåæø0123456789");
        assertThat(CharacterGroup.ALPHANUMERIC_NO_LOWERCASE.getChars()).isEqualTo("abcdefghijklmnopqrstuvwxyzåæø0123456789");
        assertThat(CharacterGroup.ALPHANUMERIC_NO_UPPERCASE.getChars()).isEqualTo("ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØ0123456789");
    }

    @Test
    void charsOf_shouldParseCharacterGroup() {
        assertThat(CharacterGroup.charsOf("alphanumeric").get().equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
        assertThat(CharacterGroup.charsOf("ALPHANUMERIC").get().equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
        assertThat(CharacterGroup.charsOf("AlPhANuMeRiC").get().equals("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
        assertThat(CharacterGroup.charsOf("lphanumeric").orElse("foo").equals("foo"));
        assertThat(CharacterGroup.charsOf(null).orElse("foo").equals("foo"));
        assertThat(CharacterGroup.charsOf("").orElse("foo").equals("foo"));
    }

}