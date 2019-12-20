package no.ssb.dapla.dlp.pseudo.key.service;

import no.ssb.dapla.dlp.pseudo.key.PseudoKey;

public interface PseudoKeyService {
    /**
     * Retrieve key used by pseudo functions
     */
    PseudoKey getKey(String keyId);
}
