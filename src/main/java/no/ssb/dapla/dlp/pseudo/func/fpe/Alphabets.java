package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.idealista.fpe.config.Alphabet;
import lombok.experimental.UtilityClass;
import no.ssb.dapla.dlp.pseudo.func.text.CharacterGroup;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncException;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class Alphabets {

    private static final Joiner JOINER = Joiner.on("").skipNulls();
    private static final Splitter PLUS_SPLITTER = Splitter.on("+").omitEmptyStrings().trimResults();

    /**
     * Return an FPE alphabet composed of characters defined in the supplied customCharacters string + CharacterGroups.
     * <pre>
     * alphabetOf("abc", CharacterGroup.DIGIT, CharacterGroup.WHITESPACE) // -> 'abc0123456789' and whitespace characters
     * </pre>
     */
    public static Alphabet alphabetOf(String customChars, CharacterGroup... charGroups) {
        StringBuilder sb = new StringBuilder(customChars);
        if (charGroups != null) {
            for (CharacterGroup cg : charGroups) {
                if (cg != null) {
                    sb.append(cg.getChars());
                }
            }
        }

        return newAlphabet(distinctCharacters(sb.toString()));
    }

    /**
     * Return an FPE alphabet composed of characters defined in the supplied CharacterGroups.
     * <pre>
     * alphabetOf(CharacterGroup.ALPHANUMERIC, CharacterGroup.WHITESPACE) // -> alphanumeric and whitespace characters
     * </pre>
     */
    public static Alphabet alphabetOf(CharacterGroup... charGroups) {
        return alphabetOf("", charGroups);
    }

    /**
     * Return an FPE alphabet composed of custom characters defined in the supplied string.
     * <pre>
     * alphabetOf("abc") // -> the characters 'a', 'b, and 'c'
     * </pre>
     */
    public static Alphabet alphabetOf(String customChars) {
        return alphabetOf(customChars, (CharacterGroup) null);
    }

    /**
     * Parse an alphabet string such as alphanumeric+whitespace+foo
     * "Fragments" in the string can reference CharacterGroup items.
     *
     * Alphabet names can be created by using {@link #alphabetNameOf(CharacterGroup...)}
     *
     * @param alphabetName plus-separated string
     * @return an Alphabet composed of characters from referenced CharacterGroups
     * and custom characters
     */
    public static Alphabet fromAlphabetName(String alphabetName) {
        StringBuilder sb = new StringBuilder();
        PLUS_SPLITTER.splitToList(alphabetName).stream().forEach(s ->
              sb.append(CharacterGroup.charsOf(s).orElse(s))
        );
        return alphabetOf(sb.toString());
    }

    /**
     * Deduce an alphabet name from CharacterGroup items.
     */
    public static String alphabetNameOf(CharacterGroup... charGroups) {
        return alphabetNameOf(null, charGroups);
    }

    /**
     * Deduce an alphabet name from CharacterGroup items and a set of custom characters.
     */
    public static String alphabetNameOf(String customChars, CharacterGroup... charGroups) {
        if (charGroups == null || charGroups.length == 0) {
            return customChars;
        }

        String name = Arrays.asList(charGroups).stream()
          .filter(Objects::nonNull)
          .map(cg -> cg.name().toLowerCase())
          .collect(Collectors.joining("+"));
        if (customChars != null && ! customChars.isEmpty()) {
            name += "+" + customChars;
        }

        return name;
    }

    /**
     * Eliminate duplicate characters from one or more strings
     *
     * @return a string with only distinct characters from the supplied fragments
     */
    static String distinctCharacters(String... fragments) {
        StringBuilder sb = new StringBuilder();
        if (fragments != null) {
            JOINER.join(fragments).chars().distinct()
              .forEach(c -> sb.append((char) c));
        }
        return sb.toString();
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
