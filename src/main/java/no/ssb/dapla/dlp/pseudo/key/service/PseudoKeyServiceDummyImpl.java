package no.ssb.dapla.dlp.pseudo.key.service;

import no.ssb.dapla.dlp.pseudo.key.PseudoKey;

import java.util.Map;
import java.util.Optional;

public class PseudoKeyServiceDummyImpl implements PseudoKeyService {

    static final String DUMMY_KEY1 = "01DWENC90WW9K41EN0QS2Q23X4";
    static final String DUMMY_KEY2 = "01DWENCY4JP5VFWKTTS0KBA8QZ";
    static final String DUMMY_KEY3 = "01DWENDBC2EHDSWX52JDQFA1BG";

    private final Map<String, PseudoKey> keys = Map.of(
      DUMMY_KEY1, new PseudoKey("8weo9VlQTuPqxjVWaHAupOdCwNpn4CFz"),
      DUMMY_KEY2, new PseudoKey("kz3Z3XIMMVdZ7q9VHSQjWoNIMbGBT1cI"),
      DUMMY_KEY3, new PseudoKey("ERLWBPuZXJLig4THqAIr1SxPfArkpnYz")
    );

    @Override
    public PseudoKey getKey(String keyId) {
        return Optional.ofNullable(keys.get(keyId)).orElseThrow(
          () -> new PseudoKeyNotFoundException(keyId));
    }

    public static class PseudoKeyNotFoundException extends PseudoKeyServiceException {
        public PseudoKeyNotFoundException(String keyId) {
            super("Unable to find pseudo key with ID='" + keyId + "'");
        }
    }

}
