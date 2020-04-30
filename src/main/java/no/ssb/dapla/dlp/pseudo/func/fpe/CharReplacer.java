package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.google.common.base.Joiner;
import com.google.common.primitives.Chars;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

class CharReplacer {
    @EqualsAndHashCode
    @Getter
    static class ReplacementResult {
        private String result;
        private String replacementString;
        private final Set<Character> replacedChars = new LinkedHashSet<>();
        public boolean hasReplacedChars() {
            return ! replacedChars.isEmpty();
        }
    }

    private CharReplacer() {}

    static ReplacementResult replace(String in, String replacement, char... allowedChars) {
        ReplacementResult res = new ReplacementResult();
        if (in == null) {
            return new ReplacementResult();
        }

        Set<Character> allowedCharsSet = new HashSet<>(Chars.asList(allowedChars == null ? new char[0] : allowedChars));
        replacement = Optional.ofNullable(replacement).orElse("X");
        Set<Character> replacementCharsSet = new LinkedHashSet<>(Chars.asList(replacement.toCharArray()));
        replacementCharsSet.retainAll(allowedCharsSet);
        res.replacementString = Joiner.on("").join(replacementCharsSet);

        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            if (allowedCharsSet.contains(c)) {
                sb.append(c);
            }
            else {
                res.getReplacedChars().add(c);
                sb.append(res.replacementString);
            }
        }
        res.result = sb.toString();

        return res;
    }
}
