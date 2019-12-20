package no.ssb.dapla.dlp.pseudo.func.fpe;

import com.idealista.fpe.config.Alphabet;
import com.idealista.fpe.config.Domain;
import com.idealista.fpe.config.GenericDomain;
import com.idealista.fpe.config.GenericTransformations;
import com.idealista.fpe.transformer.IntToTextTransformer;
import com.idealista.fpe.transformer.TextToIntTransformer;
import lombok.experimental.UtilityClass;

@UtilityClass
class Domains {

    public static Domain domainOf(Alphabet alphabet) {
        TextToIntTransformer textToIntTransformer = new GenericTransformations(alphabet.availableCharacters());
        IntToTextTransformer intToTextTransformer = new GenericTransformations(alphabet.availableCharacters());
        return new GenericDomain(alphabet, textToIntTransformer, intToTextTransformer);
    }

}
