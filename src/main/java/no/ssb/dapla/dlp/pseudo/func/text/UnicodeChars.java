package no.ssb.dapla.dlp.pseudo.func.text;

import com.google.common.primitives.Chars;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.*;

/**
 * See https://en.wikipedia.org/wiki/List_of_Unicode_characters
 */
public class UnicodeChars {

    private UnicodeChars() {}

    private static final Map<Character.UnicodeBlock, List<Character>> unicodeBlockToCharsMap = new HashMap<>();

    /**
     * Return List of Characters contained in the specified {@link java.lang.Character.UnicodeBlock}.
     */
    public static List<Character> listOf(Character.UnicodeBlock unicodeBlock) {
        if (!unicodeBlockToCharsMap.containsKey(unicodeBlock)) {
            unicodeBlockToCharsMap.put(unicodeBlock, computeCharsOf(unicodeBlock));
        }

        return unicodeBlockToCharsMap.get(unicodeBlock);
    }

    private static List<Character> listOf(CharQuery q) {
        return listOf(q.unicodeBlock, q.setOfIncludedCharTypes());
    }

    public static List<Character> listOf(Character.UnicodeBlock unicodeBlock, CharType... includedCharTypes) {
        Set<CharType> included = new HashSet<>();
        if (includedCharTypes != null) {
            Collections.addAll(included, includedCharTypes);
        }

        return listOf(unicodeBlock, included);
    }

    static List<Character> listOf(Character.UnicodeBlock unicodeBlock, Set<CharType> includedCharTypes) {
        List<Character> chars = listOf(unicodeBlock);
        return chars.stream()
          .filter(c -> {
              if (includedCharTypes.isEmpty()) {
                  return true;
              }
              else if (includedCharTypes.contains(LETTERS) && !Character.isLetter(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(DIGITS) && !Character.isDigit(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(ALPHANUMERIC) && !Character.isLetterOrDigit(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(SPACE) && !Character.isSpaceChar(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(WHITESPACE) && !Character.isWhitespace(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(SYMBOLS) && (Character.isLetterOrDigit(c) || Character.isWhitespace(c) || Character.isISOControl(c))) {
                  return false;
              }
              else if (includedCharTypes.contains(LOWERCASE) && !Character.isLowerCase(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(UPPERCASE) && !Character.isUpperCase(c)) {
                  return false;
              }
              else if (includedCharTypes.contains(CONTROL) && !Character.isISOControl(c)) {
                  return false;
              }

              return true;
          })

          .collect(Collectors.toList());
    }

    public static List<Character> listOf(CharQuery... queries) {
        return Arrays.asList(queries).stream()
          .flatMap(q -> listOf(q).stream())
          .sorted()
          .distinct()
          .collect(Collectors.toList());
    }

    public static char[] arrayOf(Character.UnicodeBlock unicodeBlock) {
        List<Character> chars = listOf(unicodeBlock);
        return (chars == null) ? null : Chars.toArray(chars);
    }

    public static char[] arrayOf(Character.UnicodeBlock unicodeBlock, CharType... charTypes) {
        List<Character> chars = listOf(unicodeBlock, charTypes);
        return (chars == null) ? null : Chars.toArray(chars);
    }

    public static char[] arrayOf(CharQuery... queries) {
        List<Character> chars = listOf(queries);
        return (chars == null) ? null : Chars.toArray(chars);
    }

    public static String stringOf(Character.UnicodeBlock unicodeBlock) {
        return listOf(unicodeBlock).stream().map(Object::toString).collect(Collectors.joining());
    }

    public static String stringOf(Character.UnicodeBlock unicodeBlock, CharType... charTypes) {
        return listOf(unicodeBlock, charTypes).stream().map(Object::toString).collect(Collectors.joining());
    }

    public static String stringOf(CharQuery... queries) {
        return listOf(queries).stream().map(Object::toString).collect(Collectors.joining());
    }

    private static List<Character> computeCharsOf(Character.UnicodeBlock unicodeBlock) {
        List<Character> chars = new ArrayList<>();
        for (int cp = 0; cp <= Character.MAX_VALUE; cp++) {
            if (Character.isISOControl(cp)) {
                continue;
            }
            char c = (char) cp;
            if (Character.UnicodeBlock.of(c) == unicodeBlock) {
                chars.add(c);
            } else if (! chars.isEmpty()) {
                // Since unicode blocks are contiguous, there's no need to seek further
                break;
            }
        }

        return chars;
    }

    public enum CharType {
        LETTERS, DIGITS, ALPHANUMERIC, SPACE, WHITESPACE, SYMBOLS, UPPERCASE, LOWERCASE, CONTROL;
    }

    public static CharQuery subset(Character.UnicodeBlock unicodeBlock) {
        return new CharQuery(unicodeBlock);
    }

    @RequiredArgsConstructor
    public static class CharQuery {
        private final Character.UnicodeBlock unicodeBlock;
        private CharType[] includedCharTypes;

        public CharQuery and(CharType... charTypes) {
            this.includedCharTypes = charTypes;
            return this;
        }

        public Set<CharType> setOfIncludedCharTypes() {
            Set<CharType> set = new HashSet<>();
            if (includedCharTypes != null) {
                Collections.addAll(set, includedCharTypes);
            }
            return set;
        }

    }
}
