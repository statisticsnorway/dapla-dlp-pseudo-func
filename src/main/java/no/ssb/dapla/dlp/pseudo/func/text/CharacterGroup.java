package no.ssb.dapla.dlp.pseudo.func.text;

import no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Character.UnicodeBlock.BASIC_LATIN;
import static java.lang.Character.UnicodeBlock.LATIN_1_SUPPLEMENT;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.subset;

public enum CharacterGroup {
    /**
     * The basic latin uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZ}
     */
    LETTERS_UPPERCASE(UnicodeChars.stringOf(
      subset(BASIC_LATIN).and(CharType.UPPERCASE))
    ),

    /**
     * The basic latin lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyz}
     */
    LETTERS_LOWERCASE(UnicodeChars.stringOf(
      subset(BASIC_LATIN).and(CharType.LOWERCASE))
    ),

    /**
     * The basic latin letters (lower- and uppercase)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz}
     */
    LETTERS(LETTERS_UPPERCASE, LETTERS_LOWERCASE),

    /**
     * Norwegian extended letters (uppercase)
     * {@code ÅÆØ}
     */
    LETTERS_EXT_NO_UPPERCASE("ÅÆØ"),

    /**
     * Norwegian extended letters (lowercase)
     * {@code åæø}
     */
    LETTERS_EXT_NO_LOWERCASE("åæø"),

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
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÅÆØabcdefghijklmnopqrstuvwxyzåæø}
     */
    LETTERS_NO(LETTERS_NO_UPPERCASE, LETTERS_NO_LOWERCASE),

    /**
     * Extended latin letters (uppercase)
     * {@code ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ}
     */
    LETTERS_EXT_UPPERCASE(UnicodeChars.stringOf(
      subset(LATIN_1_SUPPLEMENT).and(CharType.UPPERCASE)
    )),

    /**
     * Extended latin letters (lowercase)
     * {@code ªµºßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ}
     */
    LETTERS_EXT_LOWERCASE(UnicodeChars.stringOf(
      subset(LATIN_1_SUPPLEMENT).and(CharType.LOWERCASE)
    )),

    /**
     * Extended latin letters (lower- and uppercase)
     * {@code ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞªµºßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿ}
     */
    LETTERS_EXT(LETTERS_EXT_UPPERCASE, LETTERS_EXT_LOWERCASE),

    /**
     * All lowercase letters (including extended)
     * {@code ªµºßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿabcdefghijklmnopqrstuvwxyz}
     */
    LETTERS_ALL_LOWERCASE(LETTERS_EXT_LOWERCASE, LETTERS_LOWERCASE),

    /**
     * All uppercase letters (including extended)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞ}
     */
    LETTERS_ALL_UPPERCASE(LETTERS_UPPERCASE, LETTERS_EXT_UPPERCASE),

    /**
     * All letters (lower- and uppercase, including extended)
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝÞªµºßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýþÿabcdefghijklmnopqrstuvwxyz}
     */
    LETTERS_ALL(LETTERS_ALL_UPPERCASE, LETTERS_ALL_LOWERCASE),

    /**
     * Numeric characters
     * {@code 0123456789}
     */
    DIGITS("0123456789"),

    /**
     * Symbol characters
     * {@code !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~¡¢£¤¥¦§¨©«¬­®¯°±²³´¶·¸¹»¼½¾¿×÷}
     */
    SYMBOLS("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~¡¢£¤¥¦§¨©«¬\u00AD®¯°±²³´¶·¸¹»¼½¾¿×÷"),

    /**
     * The space character.
     */
    SPACE(" "),

    /**
     * Any characters that are considered whitespace.
     */
    WHITESPACE(UnicodeChars.stringOf(
      subset(BASIC_LATIN).and(CharType.WHITESPACE),
      subset(LATIN_1_SUPPLEMENT).and(CharType.WHITESPACE))
    ),

    /**
     * Control characters used to control the interpretation or display of text.
     * These characters themselves have no visual or spatial representation.
     */
    CONTROL(UnicodeChars.stringOf(
      subset(BASIC_LATIN).and(CharType.CONTROL))
    ),

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
     * Any non-control from the basic and exteded latin unicode charset
     */
    ANYCHAR(UnicodeChars.stringOf(
      subset(BASIC_LATIN).and(CharType.ALPHANUMERIC),
      subset(BASIC_LATIN).and(CharType.SYMBOLS),
      subset(BASIC_LATIN).and(CharType.WHITESPACE),
      subset(LATIN_1_SUPPLEMENT).and(CharType.ALPHANUMERIC),
      subset(LATIN_1_SUPPLEMENT).and(CharType.SYMBOLS),
      subset(LATIN_1_SUPPLEMENT).and(CharType.WHITESPACE)
    ))

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
