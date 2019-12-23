package no.ssb.dapla.dlp.pseudo.key.service;

import no.ssb.dapla.dlp.pseudo.key.service.PseudoKeyServiceDummyImpl.PseudoKeyNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PseudoKeyServiceDummyImplTest {
    private final PseudoKeyService pseudoKeyService = new PseudoKeyServiceDummyImpl();

    @Test
    void existingKey_getKey_shouldReturnKey() {
        assertThat(pseudoKeyService.getKey(PseudoKeyServiceDummyImpl.DUMMY_KEY1)).isNotNull();
    }

    @Test
    void nonExistingKey_getKey_throwsPseudoKeyNotFoundException() {
        PseudoKeyNotFoundException e = assertThrows(PseudoKeyNotFoundException.class, () -> {
            pseudoKeyService.getKey("blah");
        });
        assertThat(e.getMessage()).isEqualTo("Unable to find pseudo key with ID='blah'");
    }
}