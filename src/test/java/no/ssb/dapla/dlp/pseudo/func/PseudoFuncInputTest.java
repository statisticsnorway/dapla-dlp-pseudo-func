package no.ssb.dapla.dlp.pseudo.func;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PseudoFuncInputTest {

    @Test
    void singleObject_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncInput.of("foo").getStringValues()).containsExactly("foo");
    }

    @Test
    void listOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncInput.of(ImmutableList.of("foo", "bar")).getStringValues()).containsExactly("foo", "bar");
    }

    @Test
    void arrayOfObjects_newPseudoFuncOutput_shouldPopulateInternalListProperly() {
        assertThat(PseudoFuncInput.of(new String[] {"foo", "bar"}).getStringValues()).containsExactly("foo", "bar");
    }

}