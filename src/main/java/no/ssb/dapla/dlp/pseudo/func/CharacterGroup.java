package no.ssb.dapla.dlp.pseudo.func;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public enum CharacterGroup {
    /**
     * The default english uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZ}
     */
    LETTERS_UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

    /**
     * The default english lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyz}
     */
    LETTERS_LOWERCASE("abcdefghijklmnopqrstuvwxyz"),

    /**
     * The default english letters (lower- and uppercase)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz}
     */
    LETTERS(LETTERS_UPPERCASE, LETTERS_LOWERCASE),

    /**
     * Norwegian extended letters (uppercase)
     * {@code ÆØÅ}
     */
    LETTERS_EXT_NO_UPPERCASE( "ÅÆØ"),

    /**
     * Norwegian extended letters (lowercase)
     * {@code æøå}
     */
    LETTERS_EXT_NO_LOWERCASE("åæø"),

    /**
     * Norwegian extended letters (lower- and uppercase)
     * {@code ÅÆØåæø}
     */
    LETTERS_EXT_NO(LETTERS_EXT_NO_UPPERCASE, LETTERS_EXT_NO_LOWERCASE),

    /**
     * All norwegian uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØ}
     */
    LETTERS_NO_UPPERCASE(LETTERS_UPPERCASE, LETTERS_EXT_NO_UPPERCASE),

    /**
     * All norwegian lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyzåæø}
     */
    LETTERS_NO_LOWERCASE(LETTERS_LOWERCASE, LETTERS_EXT_NO_LOWERCASE),

    /**
     * All norwegian letters (lower- and uppercase)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅabcdefghijklmnopqrstuvwxyzæøå}
     */
    LETTERS_NO(LETTERS_NO_UPPERCASE, LETTERS_NO_LOWERCASE),

    /**
     * Extended ascii letters (uppercase)
     * {@code ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ}
     */
    LETTERS_EXT_UPPERCASE("ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ"),

    /**
     * Extended ascii letters (lowercase)
     * {@code àáâãäåæçèéêëìíîïðñòóôõöøùúûüýÞß}
     */
    LETTERS_EXT_LOWERCASE("àáâãäåæçèéêëìíîïðñòóôõöøùúûüýþß"),

    /**
     * Extended ascii letters (lower- and uppercase)
     * {@code ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþß}
     */
    LETTERS_EXT(LETTERS_EXT_UPPERCASE, LETTERS_EXT_LOWERCASE),

    /**
     * All lowercase letters (including extended)
     * {@code abcdefghijklmnopqrstuvwxyzàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþß}
     */
    LETTERS_ALL_LOWERCASE(LETTERS_EXT_LOWERCASE, LETTERS_LOWERCASE),

    /**
     * All uppercase letters (including extended)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ}
     */
    LETTERS_ALL_UPPERCASE(LETTERS_UPPERCASE, LETTERS_EXT_UPPERCASE),

    /**
     * All letters (lower- and uppercase, including extended)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞabcdefghijklmnopqrstuvwxyzàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþß}
     */
    LETTERS_ALL(LETTERS_ALL_UPPERCASE, LETTERS_ALL_LOWERCASE),

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
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789}
     */
    ALPHANUMERIC(LETTERS, DIGITS),

    /**
     * Default lowercase alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyz0123456789}
     */
    ALPHANUMERIC_LOWERCASE(LETTERS_LOWERCASE, DIGITS),

    /**
     * Default lowercase alphanumeric characters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789}
     */
    ALPHANUMERIC_UPPERCASE(LETTERS_UPPERCASE, DIGITS),

    /**
     * Norwegian alphanumeric characters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØabcdefghijklmnopqrstuvwxyzåæø0123456789}
     */
    ALPHANUMERIC_NO(LETTERS_NO, DIGITS),

    /**
     * Norwegian lowercase alphanumeric characters
     * {@code abcdefghijklmnopqrstuvwxyzåæø0123456789}
     */
    ALPHANUMERIC_NO_LOWERCASE(LETTERS_LOWERCASE, LETTERS_EXT_NO_LOWERCASE, DIGITS),

    /**
     * Norwegian uppercase alphanumeric characters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØ0123456789}
     */
    ALPHANUMERIC_NO_UPPERCASE(LETTERS_UPPERCASE, LETTERS_EXT_NO_UPPERCASE, DIGITS),

    /**
     * All (including extended) alphanumeric characters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØabcdefghijklmnopqrstuvwxyzåæø0123456789}
     */
    ALPHANUMERIC_ALL(LETTERS_ALL, DIGITS),

    /**
     * All alphanumeric lowercase characters (including extended)
     * {@code abcdefghijklmnopqrstuvwxyzàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþß0123456789}
     */
    ALPHANUMERIC_ALL_LOWERCASE(LETTERS_LOWERCASE, LETTERS_EXT_LOWERCASE, DIGITS),

    /**
     * All alphanumeric uppercase characters (including extended)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝ0123456789}
     */
    ALPHANUMERIC_ALL_UPPERCASE(LETTERS_UPPERCASE, LETTERS_EXT_NO_UPPERCASE, DIGITS),

    /**
     * Any characters
     */
    ANYCHAR(ALPHANUMERIC_ALL, PUNCTUATION, WHITESPACE)

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
