package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.FormatPreservingEncryption;
import com.idealista.fpe.builder.FormatPreservingEncryptionBuilder;
import lombok.extern.slf4j.Slf4j;
import no.ssb.dapla.dlp.pseudo.func.AbstractPseudoFunc;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncConfig;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncInput;
import no.ssb.dapla.dlp.pseudo.func.PseudoFuncOutput;

import static no.ssb.dapla.dlp.pseudo.func.fpe.Domains.domainOf;

@Slf4j
public class FpeFunc extends AbstractPseudoFunc {
    private final FpeFuncConfig config;
    private final FormatPreservingEncryption fpe;
    private final FpeConfigService fpeConfigService = new FpeConfigService();

    public FpeFunc(PseudoFuncConfig genericConfig) {
        super(genericConfig.getFuncName());
        config = fpeConfigService.resolve(genericConfig);

        fpe = FormatPreservingEncryptionBuilder
          .ff1Implementation()
          .withDomain(domainOf(config.getAlphabet()))
          .withDefaultPseudoRandomFunction(config.getKey())
          .withDefaultLengthRange()
          .build();
    }

    @Override
    public PseudoFuncOutput apply(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        for (String plain : input.getStringValues()) {
            output.add(fpe.encrypt(plain, tweakOf(input)));
        }
        return output;
    }

    @Override
    public PseudoFuncOutput restore(PseudoFuncInput input) {
        PseudoFuncOutput output = new PseudoFuncOutput();
        for (String plain : input.getStringValues()) {
            output.add(fpe.decrypt(plain, tweakOf(input)));
        }
        return output;
    }

    // TODO: Implement this, some rationale here:
    // https://crypto.stackexchange.com/questions/10903/what-are-the-uses-of-tweaks-in-block-ciphers
    byte[] tweakOf(PseudoFuncInput input) {
        return new byte[0];
    }

}
