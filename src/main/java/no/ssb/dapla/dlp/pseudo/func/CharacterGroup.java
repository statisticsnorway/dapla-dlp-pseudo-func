package no.ssb.dapla.dlp.pseudo.func;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum CharacterGroup {
    /**
     * The default english lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyz}
     */
    LETTERS_LOWERCASE("abcdefghijklmnopqrstuvwxyz"),

    /**
     * The default english uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZ}
     */
    LETTERS_UPPERCASE(LETTERS_LOWERCASE.getChars().toUpperCase()),

    /**
     * The default english letters (lower- and uppercase)
     * {@code abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ}
     */
    LETTERS(LETTERS_LOWERCASE, LETTERS_UPPERCASE),

    /**
     * Norwegian special letters (lowercase)
     * {@code æøå}
     */
    LETTERS_NO_SPECIAL_LOWERCASE("æøå"),

    /**
     * Norwegian special letters (uppercase)
     * {@code ÆØÅ}
     */
    LETTERS_NO_SPECIAL_UPPERCASE( "ÆØÅ"),

    /**
     * Norwegian special letters (lower- and uppercase)
     * {@code æøåÆØÅ}
     */
    LETTERS_NO_SPECIAL( LETTERS_NO_SPECIAL_LOWERCASE, LETTERS_NO_SPECIAL_UPPERCASE),

    /**
     * All norwegian lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyzæøå}
     */
    LETTERS_NO_LOWERCASE(LETTERS_LOWERCASE, LETTERS_NO_SPECIAL_LOWERCASE),

    /**
     * All norwegian uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ}
     */
    LETTERS_NO_UPPERCASE(LETTERS_UPPERCASE, LETTERS_NO_SPECIAL_UPPERCASE),

    /**
     * All norwegian letters (lower- and uppercase)
     * {@code abcdefghijklmnopqrstuvwxyzæøåABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ}
     */
    LETTERS_NO(LETTERS_NO_LOWERCASE, LETTERS_NO_UPPERCASE),

    /**
     * Swedish special letters (lowercase)
     * {@code äöå}
     */
    LETTERS_SE_SPECIAL_LOWERCASE("äöå"),

    /**
     * Swedish special letters (uppercase)
     * {@code ÄÖÅ}
     */
    LETTERS_SE_SPECIAL_UPPERCASE("ÄÖÅ"),

    /**
     * Swedish special letters (lower- and uppercase)
     * {@code äöåÄÖÅ}
     */
    LETTERS_SE_SPECIAL(LETTERS_SE_SPECIAL_LOWERCASE, LETTERS_SE_SPECIAL_UPPERCASE),

    /**
     * All swedish lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyzäöå}
     */
    LETTERS_SE_LOWERCASE(LETTERS_LOWERCASE, LETTERS_SE_SPECIAL_LOWERCASE),

    /**
     * All swedish uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÅ}
     */
    LETTERS_SE_UPPERCASE(LETTERS_UPPERCASE, LETTERS_SE_SPECIAL_UPPERCASE),

    /**
     * All swedish letters (lower- and uppercase)
     * {@code abcdefghijklmnopqrstuvwxyzäöåABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÅ}
     */
    LETTERS_SE(LETTERS_SE_LOWERCASE, LETTERS_SE_UPPERCASE),

    /**
     * Numeric characters
     * {@code 0123456789}
     */
    DIGITS("0123456789"),

    /**
     * ASCII characters which are considered punctuation characters
     * {@code !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~}
     */
    PUNCTUATION("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"),

    /**
     * ASCII characters that are considered whitespace.
     */
    WHITESPACE(" "),

    /**
     * Special ASCII characters, such as linebreaks, formfeed, tabs, etc.
     */
    SPECIAL("\t\f\r\n"),

    /**
     * Default alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789}
     */
    ALPHANUMERIC(LETTERS, DIGITS),

    /**
     * Default lowercase alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyz0123456789}
     */
    ALPHANUMERIC_LOWERCASE(LETTERS_LOWERCASE, DIGITS),

    /**
     * Default lowercase alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyz0123456789}
     */
    ALPHANUMERIC_UPPERCASE(LETTERS_UPPERCASE, DIGITS),

    /**
     * Norwegian alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyzæøåABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ0123456789}
     */
    ALPHANUMERIC_NO(LETTERS_NO, DIGITS),

    /**
     * Norwegian lowercase alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyzæøå0123456789}
     */
    ALPHANUMERIC_NO_LOWERCASE(LETTERS_LOWERCASE, LETTERS_NO_SPECIAL_LOWERCASE, DIGITS),

    /**
     * Norwegian uppercase alphanumeric characters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ0123456789}
     */
    ALPHANUMERIC_NO_UPPERCASE(LETTERS_UPPERCASE, LETTERS_NO_SPECIAL_UPPERCASE, DIGITS)

    ;

    private final String chars;

    CharacterGroup(String chars) {
        this.chars = chars;
    }

    CharacterGroup(CharacterGroup... characterGroups) {
        this.chars = Arrays.asList(characterGroups).stream()
          .map(CharacterGroup::getChars)
          .collect(Collectors.joining());
    }

    /**
     * Locate a CharacterGroup by name (case insensitive) and return its characters - if defined.
     */
    public static Optional<String> charsOf(String s) {
        for (CharacterGroup cg : CharacterGroup.values()) {
            if (cg.name().equalsIgnoreCase(s)) {
                return Optional.of(cg.getChars());
            }
        }
        return Optional.empty();
    }

    public String getChars() {
        return chars;
    }
}
