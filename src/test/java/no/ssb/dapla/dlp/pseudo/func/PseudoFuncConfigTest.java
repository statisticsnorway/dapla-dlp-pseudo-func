package no.ssb.dapla.dlp.pseudo.func;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PseudoFuncConfigTest {
    @Test
    void json_constructPseudoFuncConfg_shouldParseSuccessfully() {
        String json = "{\"name\":\"fpe-custom-1\",\"impl\":\"no.ssb.dapla.dlp.pseudo.func.FpeFunc\",\"keyId\":\"411f2af1-7588-4c7f-95e4-1c15d82ef202\",\"alphabet\":\"abcdefghij123_ \"}";
        assertThat(new PseudoFuncConfig(json)).isNotNull();
    }
}