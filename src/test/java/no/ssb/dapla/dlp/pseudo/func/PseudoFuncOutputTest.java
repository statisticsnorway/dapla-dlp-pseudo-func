package no.ssb.dapla.dlp.pseudo.func;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PseudoFuncOutputTest {

    @Test
    void singleObject_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of("foo").getStringValues()).containsExactly("foo");
    }

    @Test
    void listOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of(List.of("foo", "bar")).getStringValues()).containsExactly("foo", "bar");
    }

    @Test
    void arrayOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncOutput.of(new String[] {"foo", "bar"}).getStringValues()).containsExactly("foo", "bar");
    }

}