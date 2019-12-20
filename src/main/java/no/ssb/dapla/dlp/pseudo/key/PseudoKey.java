package no.ssb.dapla.dlp.pseudo.key;

import lombok.Value;

import java.nio.charset.StandardCharsets;

@Value
public class PseudoKey {
    private final String key;

    public byte[] asByteArray() {
        return key.getBytes(StandardCharsets.UTF_8);
    }
}
