package no.ssb.dapla.dlp.pseudo.key.service;

import no.ssb.dapla.dlp.pseudo.key.PseudoKey;

import java.util.Map;
import java.util.Optional;

public class PseudoKeyServiceDummyImpl implements PseudoKeyService {

    private final Map<String, PseudoKey> keys = Map.of(
      "01DWENC90WW9K41EN0QS2Q23X4", new PseudoKey("8weo9VlQTuPqxjVWaHAupOdCwNpn4CFz"),
      "01DWENCY4JP5VFWKTTS0KBA8QZ", new PseudoKey("kz3Z3XIMMVdZ7q9VHSQjWoNIMbGBT1cI"),
      "01DWENDBC2EHDSWX52JDQFA1BG", new PseudoKey("ERLWBPuZXJLig4THqAIr1SxPfArkpnYz")
    );

    @Override
    public PseudoKey getKey(String keyId) {
        return Optional.ofNullable(keys.get(keyId)).orElseThrow(
          () -> new PseudoKeyNotFoundException(keyId));
    }

    public static class PseudoKeyNotFoundException extends PseudoKeyServiceException {
        public PseudoKeyNotFoundException(String keyId) {
            super("Unable to find pseudo key with ID=" + keyId + "'");
        }
    }

}
