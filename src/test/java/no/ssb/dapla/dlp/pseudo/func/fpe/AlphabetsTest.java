package no.ssb.dapla.dlp.pseudo.func.fpe;

import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;
import org.junit.jupiter.api.Test;

import static no.ssb.dapla.dlp.pseudo.func.fpe.Alphabets.alphabetOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AlphabetsTest {

    @Test
    void predefinedAlphabetType_alphabetOf_shouldReturnDefault() {
        assertThat(alphabetOf(AlphabetType.ALPHANUMERIC).availableCharacters().length).isEqualTo(72);
        assertThat(alphabetOf(AlphabetType.ALPHANUMERIC_WHITESPACE).availableCharacters().length).isEqualTo(73);
        assertThat(alphabetOf(AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION).availableCharacters().length).isEqualTo(105);
        assertThat(alphabetOf(AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION_SPECIAL).availableCharacters().length).isEqualTo(109);
        assertThat(alphabetOf(AlphabetType.DIGITS).availableCharacters().length).isEqualTo(10);
        assertThat(alphabetOf(AlphabetType.DIGITS_WHITESPACE).availableCharacters().length).isEqualTo(11);
        assertThat(alphabetOf(AlphabetType.DIGITS_WHITESPACE_PUNCTUATION).availableCharacters().length).isEqualTo(43);
        assertThat(alphabetOf(AlphabetType.DIGITS_WHITESPACE_PUNCTUATION_SPECIAL).availableCharacters().length).isEqualTo(47);
    }

    @Test
    void customAlphabet_alphabetOf_shouldReturnDefault() {
        assertThat(alphabetOf("abcd1234!#$% ").availableCharacters().length).isEqualTo(13);
    }

    @Test
    void tooShortCustomAlphabet_alphabetOf_shouldFailProperly() {
        PseudoFuncException e = assertThrows(PseudoFuncException.class, () -> {
            alphabetOf("123456789");
        });
        assertThat(e.getMessage()).isEqualTo("Invalid alphabet '123456789'. Must be at least 10 characters long.");
    }

}