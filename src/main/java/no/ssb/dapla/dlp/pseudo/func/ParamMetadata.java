package no.ssb.dapla.dlp.pseudo.func;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Value
@Builder
@Accessors(fluent = true)
public class ParamMetadata {

    private static final Pattern letterPattern = Pattern.compile("[a-zA-z]");
    private static final Pattern digitPattern = Pattern.compile("[0-9]");
    private static final Pattern whitespacePattern = Pattern.compile ("[\\s]");
    private static final Pattern punctuationPattern = Pattern.compile ("[\\.,;!?]");
    private static final Pattern linebreakPattern = Pattern.compile("[" + System.lineSeparator() + "]");
    private static final Pattern otherPattern = Pattern.compile ("[@#$%&*()_+=|<>{}\\[\\]~-]");

    private static final ParamMetadata EMPTY = ParamMetadata.builder()
      .hasLetter(false)
      .hasDigit(false)
      .hasWhitespace(false)
      .hasPunctuation(false)
      .hasLinebreak(false)
      .hasOther(false)
      .build();

    private final boolean hasLetter;
    private final boolean hasDigit;
    private final boolean hasPunctuation;
    private final boolean hasWhitespace;
    private final boolean hasLinebreak;
    private final boolean hasOther;

    /**
     * Parse a string and return a ParamMetadata that hints on the character types
     * the string consists of.
     */
    public static ParamMetadata parse(String s) {
        return (s == null) ? EMPTY : ParamMetadata.builder()
          .hasLetter(letterPattern.matcher(s).find())
          .hasDigit(digitPattern.matcher(s).find())
          .hasWhitespace(whitespacePattern.matcher(s).find())
          .hasPunctuation(punctuationPattern.matcher(s).find())
          .hasLinebreak(linebreakPattern.matcher(s).find())
          .hasOther(otherPattern.matcher(s).find())
          .build();
    }

    /**
     * Merge a collection of ParamMetadata objects to describe ALL different chacter types
     * that are the ParamMetadatas consists of.
     */
    public static ParamMetadata merge(ParamMetadata... paramMetadataToMerge) {
        ParamMetadataBuilder merged = ParamMetadata.builder();
        Arrays.asList(paramMetadataToMerge).stream().forEach(pm -> {
            if (pm.hasLetter()) {
                merged.hasLetter(true);
            }
            if (pm.hasDigit()) {
                merged.hasDigit(true);
            }
            if (pm.hasWhitespace()) {
                merged.hasWhitespace(true);
            }
            if (pm.hasPunctuation()) {
                merged.hasPunctuation(true);
            }
            if (pm.hasLinebreak()) {
                merged.hasLinebreak(true);
            }
            if (pm.hasOther) {
                merged.hasOther(true);
            }
          }
        );

        return merged.build();
    }

    public Set<String> types() {
        Set<String> types = new LinkedHashSet<>();
        if (hasLetter()) {
            types.add("letter");
        }
        if (hasDigit) {
            types.add("digit");
        }
        if (hasWhitespace()) {
            types.add("whitespace");
        }
        if (hasPunctuation) {
            types.add("punctuation");
        }
        if (hasLinebreak()) {
            types.add("linebreak");
        }
        if (hasOther) {
            types.add("other");
        }

        return types;
    }

    @Override
    public String toString() {
        return EMPTY.equals(this)
          ? "empty"
          : String.join(", ", types());
    }
}
