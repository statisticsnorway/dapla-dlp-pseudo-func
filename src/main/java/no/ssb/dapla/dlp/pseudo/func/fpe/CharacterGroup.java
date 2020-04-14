package no.ssb.dapla.dlp.pseudo.func.fpe;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CharacterGroup {
    /**
     * The default lowercase letters
     * {@code abcdefghijklmnopqrstuvwxyz}
     */
    public static final String ASCII_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * The default uppercase letters
     * {@code ABCDEFGHIJKLMNOPQRSTUVWXYZ}
     */
    public static final String ASCII_UPPERCASE = ASCII_LOWERCASE.toUpperCase();

    /**
     * Lowercase interational letters
     * {@code æãøöå}
     */
    public static final String ASCII_LOWERCASE_INTERNATIONAL = "æäøöå";


    /**
     * Uppercase interational letters
     * {@code æãøöå}
     */
    public static final String ASCII_UPPERCASE_INTERNATIONAL = ASCII_LOWERCASE_INTERNATIONAL.toUpperCase();


    /**
     * Numeric characters
     * {@code 0123456789}
     */
    public static final String DIGITS = "0123456789";

    /**
     * ASCII characters which are considered punctuation characters
     * {@code !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~}
     */
    public static final String PUNCTUATION = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    /**
     * ASCII characters that are considered whitespace.
     */
    public static final String WHITESPACE = " ";

    /**
     * Special ASCII characters, such as linebreaks, formfeed, tabs, etc.
     */
    public static final String SPECIAL = "\t\f\r\n";

}
