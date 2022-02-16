package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PseudoFuncOutputTest {

    @Test
    void singleObject_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of("foo").getStringValues()).containsExactly("foo");
    }

    @Test
    void listOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of(ImmutableList.of("foo", "bar")).getStringValues()).containsExactly("foo", "bar");
    }

    @Test
    void arrayOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of(new String[] {"foo", "bar"}).getStringValues()).containsExactly("foo", "bar");
    }

}