package no.ssb.dapla.dlp.pseudo.func.fpe;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;
import no.ssb.dapla.dlp.pseudo.func.text.CharacterGroup;
import org.junit.jupiter.api.Test;

import static no.ssb.dapla.dlp.pseudo.func.fpe.Alphabets.alphabetOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AlphabetsTest {

    @Test
    void distinctCharacters() {
        assertThat(Alphabets.distinctCharacters("abcc", "cdee", "cdf")).isEqualTo("abcdef");
        assertThat(Alphabets.distinctCharacters("abcc", null, "")).isEqualTo("abc");
        assertThat(Alphabets.distinctCharacters(null)).isEmpty();

        for (CharacterGroup cg : CharacterGroup.values()) {
            assertThat(Alphabets.distinctCharacters(cg.getChars())).isEqualTo(cg.getChars());
        }
    }

    @Test
    void customAlphabet_alphabetOf_shouldReturnDefault() {
        assertThat(alphabetOf("abcd1234!#$% ").availableCharacters()).hasSize(13);
    }

    @Test
    void tooShortCustomAlphabet_alphabetOf_shouldFailProperly() {
        PseudoFuncException e = assertThrows(PseudoFuncException.class, () -> {
            alphabetOf("123456789");
        });
        assertThat(e.getMessage()).isEqualTo("Invalid alphabet '123456789'. Must be at least 10 characters long.");
    }

    @Test
    void plusSeparatedString_parsePlusSeparatedAlphabetString_shouldReturnExpectedAlphabet() {
        assertThat(Alphabets.fromAlphabetName("alphanumeric+whitespace").availableCharacters())
          .containsExactly('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ');
        assertThat(Alphabets.fromAlphabetName("tasty+cheez+whitespace+doodles").availableCharacters())
          .containsExactly('t', 'a', 's', 'y', 'c', 'h', 'e', 'z', ' ', 'd', 'o', 'l');
    }

    @Test
    void alphabetNameOf() {
        assertThat(Alphabets.alphabetNameOf(CharacterGroup.ALPHANUMERIC)).isEqualTo("alphanumeric");
        assertThat(Alphabets.alphabetNameOf(CharacterGroup.ALPHANUMERIC, CharacterGroup.WHITESPACE))
          .isEqualTo("alphanumeric+whitespace");
        assertThat(Alphabets.alphabetNameOf("foo", CharacterGroup.ALPHANUMERIC, CharacterGroup.WHITESPACE))
          .isEqualTo("alphanumeric+whitespace+foo");
        assertThat(Alphabets.alphabetNameOf("1234567890.-_", null))
          .isEqualTo("1234567890.-_");
        assertThat(Alphabets.alphabetNameOf("1234567890.-_"))
          .isEqualTo("1234567890.-_");
    }

}