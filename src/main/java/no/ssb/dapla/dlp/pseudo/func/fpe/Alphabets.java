package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.google.common.collect.ImmutableMap;
import com.idealista.fpe.config.Alphabet;
import lombok.experimental.UtilityClass;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;

import java.util.Map;

import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.ALPHANUMERIC;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.ALPHANUMERIC_WHITESPACE;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.ALPHANUMERIC_WHITESPACE_PUNCTUATION_SPECIAL;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.DIGITS;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.DIGITS_WHITESPACE;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.DIGITS_WHITESPACE_PUNCTUATION;
import static no.ssb.dapla.dlp.pseudo.func.fpe.AlphabetType.DIGITS_WHITESPACE_PUNCTUATION_SPECIAL;

@UtilityClass
class Alphabets {

    private Map<String, Alphabet> DEFAULT_ALPHABETS = ImmutableMap.<String, Alphabet>builder()
        .put(
          ALPHANUMERIC, newAlphabet(new StringBuilder()
            .append(CharacterGroup.ASCII_LOWERCASE)
            .append(CharacterGroup.ASCII_LOWERCASE_INTERNATIONAL)
            .append(CharacterGroup.ASCII_UPPERCASE)
            .append(CharacterGroup.ASCII_UPPERCASE_INTERNATIONAL)
            .append(CharacterGroup.DIGITS)
            .toString()))

        .put(
          ALPHANUMERIC_WHITESPACE, newAlphabet(new StringBuilder()
            .append(CharacterGroup.ASCII_LOWERCASE)
            .append(CharacterGroup.ASCII_LOWERCASE_INTERNATIONAL)
            .append(CharacterGroup.ASCII_UPPERCASE)
            .append(CharacterGroup.ASCII_UPPERCASE_INTERNATIONAL)
            .append(CharacterGroup.DIGITS)
            .append(CharacterGroup.WHITESPACE)
            .toString()))

        .put(
          ALPHANUMERIC_WHITESPACE_PUNCTUATION, newAlphabet(new StringBuilder()
            .append(CharacterGroup.ASCII_LOWERCASE)
            .append(CharacterGroup.ASCII_LOWERCASE_INTERNATIONAL)
            .append(CharacterGroup.ASCII_UPPERCASE)
            .append(CharacterGroup.ASCII_UPPERCASE_INTERNATIONAL)
            .append(CharacterGroup.DIGITS)
            .append(CharacterGroup.WHITESPACE)
            .append(CharacterGroup.PUNCTUATION)
            .toString()))

      .put(
        ALPHANUMERIC_WHITESPACE_PUNCTUATION_SPECIAL, newAlphabet(new StringBuilder()
        .append(CharacterGroup.ASCII_LOWERCASE)
        .append(CharacterGroup.ASCII_LOWERCASE_INTERNATIONAL)
        .append(CharacterGroup.ASCII_UPPERCASE)
        .append(CharacterGroup.ASCII_UPPERCASE_INTERNATIONAL)
        .append(CharacterGroup.DIGITS)
        .append(CharacterGroup.WHITESPACE)
        .append(CharacterGroup.PUNCTUATION)
        .append(CharacterGroup.SPECIAL)
        .toString()))

      .put(
        DIGITS, newAlphabet(CharacterGroup.DIGITS)
      )

      .put(
        DIGITS_WHITESPACE, newAlphabet(new StringBuilder()
            .append(CharacterGroup.DIGITS)
            .append(CharacterGroup.WHITESPACE)
            .toString()))

      .put(
        DIGITS_WHITESPACE_PUNCTUATION, newAlphabet(new StringBuilder()
            .append(CharacterGroup.DIGITS)
            .append(CharacterGroup.WHITESPACE)
            .append(CharacterGroup.PUNCTUATION)
            .toString()))

      .put(
        DIGITS_WHITESPACE_PUNCTUATION_SPECIAL, newAlphabet(new StringBuilder()
            .append(CharacterGroup.DIGITS)
            .append(CharacterGroup.WHITESPACE)
            .append(CharacterGroup.PUNCTUATION)
            .append(CharacterGroup.SPECIAL)
            .toString()))

      .build();

    /**
     * Return an alphabet - either a default (defined in @{@link AlphabetType}) or
     * a custom, consisting only of characters in the provided string
     * <pre>
     * alphabetOf(AlphabetTypes.ALPHANUMERIC_WHITESPACE) // -> alphanumeric and whitespace characters
     * alphabetOf("ABC") // -> only the characters 'A', 'B' and 'C'
     * </pre>
     *
     */
    public static Alphabet alphabetOf(String s) {
        return DEFAULT_ALPHABETS.containsKey(s)
          ? DEFAULT_ALPHABETS.get(s)
          : newAlphabet(s);
    }

    /**
     * Create a new Alphabet consisting of characters in the provided String
     */
    private static Alphabet newAlphabet(String s) {
        if (s == null || s.length() < 10) {
            throw new PseudoFuncException("Invalid alphabet '" + s + "'. Must be at least 10 characters long.");
        }

        return new Alphabet() {
            @Override
            public char[] availableCharacters() {
                return s.toCharArray();
            }

            @Override
            public Integer radix() {
                return s.length();
            }
        };
    }
}
