package no.ssb.dapla.dlp.pseudo.func.text;

import com.google.common.primitives.Chars;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static no.ssb.dapla.dlp.pseudo.func.text.CharacterGroup.ALPHANUMERIC;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.CONTROL;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.DIGITS;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.LETTERS;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.LOWERCASE;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.SPACE;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.SYMBOLS;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.UPPERCASE;
import static no.ssb.dapla.dlp.pseudo.func.text.UnicodeChars.CharType.WHITESPACE;

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
        Set<CharType> included = q.setOfIncludedCharTypes();

        List<Character> chars = listOf(q.unicodeBlock);
        return chars.stream()
          .filter(c -> {
              if (included.isEmpty()) {
                  return true;
              }
              if (included.contains(LETTERS) && !Character.isLetter(c)) {
                  return false;
              }
              if (included.contains(DIGITS) && !Character.isDigit(c)) {
                  return false;
              }
              if (included.contains(ALPHANUMERIC) && !Character.isLetterOrDigit(c)) {
                  return false;
              }
              if (included.contains(SPACE) && !Character.isSpaceChar(c)) {
                  return false;
              }
              if (included.contains(WHITESPACE) && !Character.isWhitespace(c)) {
                  return false;
              }
              if (included.contains(SYMBOLS) && Character.isLetterOrDigit(c)) {
                  return false;
              }
              if (included.contains(LOWERCASE) && !Character.isLowerCase(c)) {
                  return false;
              }
              if (included.contains(UPPERCASE) && !Character.isUpperCase(c)) {
                  return false;
              }
              if (included.contains(CONTROL) && !Character.isISOControl(c)) {
                  return false;
              }

              return true;
          })

          .collect(Collectors.toList());
    }

    public static List<Character> listOf(Character.UnicodeBlock unicodeBlock, CharType... includedCharTypes) {
        Set<CharType> included = new HashSet<>();
        if (includedCharTypes != null) {
            Collections.addAll(included, includedCharTypes);
        }
        List<Character> chars = listOf(unicodeBlock);
        return chars.stream()
          .filter(c -> {
              if (included.isEmpty()) {
                  return true;
              }
              if (included.contains(LETTERS) && !Character.isLetter(c)) {
                  return false;
              }
              if (included.contains(DIGITS) && !Character.isDigit(c)) {
                  return false;
              }
              if (included.contains(ALPHANUMERIC) && !Character.isLetterOrDigit(c)) {
                  return false;
              }
              if (included.contains(SPACE) && !Character.isSpaceChar(c)) {
                  return false;
              }
              if (included.contains(WHITESPACE) && !Character.isWhitespace(c)) {
                  return false;
              }
              if (included.contains(SYMBOLS) && Character.isLetterOrDigit(c) && Character.isWhitespace(c)) {
                  return false;
              }
              if (included.contains(LOWERCASE) && !Character.isLowerCase(c)) {
                  return false;
              }
              if (included.contains(UPPERCASE) && !Character.isUpperCase(c)) {
                  return false;
              }
              if (included.contains(CONTROL) && !Character.isISOControl(c)) {
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
            } else if (chars.size() > 0) {
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
