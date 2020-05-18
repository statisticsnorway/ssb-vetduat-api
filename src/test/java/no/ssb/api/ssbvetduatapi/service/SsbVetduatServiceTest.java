package no.ssb.api.ssbvetduatapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.api.ssbvetduatapi.repository.VetduatRestRepository;
import no.ssb.api.ssbvetduatapi.util.TestResultStrings;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SsbVetduatServiceTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @Mock
    private VetduatRestRepository vetduatRestRepository;

    @InjectMocks
    SsbVetduatService ssbVetduatService = new SsbVetduatService();

    final String existsVetduatResult = "[{\"epdNr\":4608006,\"produktnavn\":\"Havrekli 950g bx Møllerens\",\"opprinnelsesland\":\"Norge\",\"gtin\":7020655840885,\"gln\":7080000757466,\"firmaNavn\":\"NORGESMØLLENE AS\",\"varegruppenavn\":\"Mel - kornblanding/frokostblanding (tørrvare)\",\"minimumsTemperaturCelcius\":4.0,\"maksimumsTemperaturCelcius\":25.0,\"ingredienser\":\"HAVREKLI\",\"bildeUrl\":\"https://vetduatapi-test.tradesolution.no/api/images/Medium/7020655840885/png\",\"mengde\":\"950\",\"mengdeType\":\"gram\",\"merkeordninger\":[\"Nøkkelhullet\"],\"deklarasjoner\":[{\"deklarasjon\":\"Energi kj pr 100 g/ml\",\"verdi\":\"1428.0000\"},{\"deklarasjon\":\"Energi kcal pr 100 g/ml\",\"verdi\":\"340.0000\"},{\"deklarasjon\":\"Fett (totalt) pr 100 g/ml\",\"verdi\":\"7.5000\"},{\"deklarasjon\":\"Mettede fettsyrer pr 100 g/ml\",\"verdi\":\"1.6000\"},{\"deklarasjon\":\"Enumettede fettsyrer pr 100 g/ml\",\"verdi\":\"2.7000\"},{\"deklarasjon\":\"Flerumettede fettsyrer pr 100 g/ml\",\"verdi\":\"2.7000\"},{\"deklarasjon\":\"Karbohydrater pr 100 g/ml\",\"verdi\":\"47.5000\"},{\"deklarasjon\":\"Sukkerarter pr 100 g/ml\",\"verdi\":\"3.1000\"},{\"deklarasjon\":\"Kostfiber pr 100 g/ml\",\"verdi\":\"15.0000\"},{\"deklarasjon\":\"Protein pr 100 g/ml\",\"verdi\":\"13.2000\"},{\"deklarasjon\":\"Salt pr 100 g/ml\",\"verdi\":\"0.0000\"}],\"allergener\":[{\"allergen\":\"Gluten\",\"verdi\":\"Inneholder\"},{\"allergen\":\"Havre gluten\",\"verdi\":\"Inneholder\"},{\"allergen\":\"Hvete gluten\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Spelt gluten\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Khorasanhvete gluten\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Rug gluten\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Bygg gluten\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Skalldyr\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Egg\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Fisk\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Peanøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Soya\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Melk\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Nøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Mandler\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Hasselnøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Valnøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Kasjunøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Pekannøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Paranøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Pistasienøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Macademiannøtter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Selleri\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Sennep\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Sesamfrø\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Svoveldioksid eller sulfitter\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Lupiner\",\"verdi\":\"Inneholder ikke\"},{\"allergen\":\"Bløtdyr\",\"verdi\":\"Inneholder ikke\"}],\"sistEndret\":\"2018-09-03T07:30:59.93+00:00\"}]";
    final String notExistsVetduatResult = "[]";

    final ObjectMapper objectMapper = new ObjectMapper();

    AtomicReference<JsonNode> resultNode = new AtomicReference<>();



    @Test
    void testSsbVetduatService_oneCode_notExists() throws Exception {
        JsonNode expected = objectMapper.readTree(TestResultStrings.singleNotExistsResult);
        when(vetduatRestRepository.callVetDuAt(anyString())).thenReturn(notExistsVetduatResult);

        ssbVetduatService.productInformationForCodes("2").thenAccept(result ->
                        resultNode.set(result));

        assertEquals(expected, resultNode.get());
    }

    @Test
    void testSsbVetduatService_oneCode_exists() throws Exception {
        JsonNode expected = objectMapper.readTree(TestResultStrings.singleExistsResult);
        when(vetduatRestRepository.callVetDuAt(anyString())).thenReturn(TestResultStrings.singleExistsResult);

        ssbVetduatService.productInformationForCodes("2").thenAccept(result ->
                resultNode.set(result));
        JsonNode actual = resultNode.get();

        assertEquals(expected, actual);
        assertEquals(1, actual.size());
    }

    @Test
    void testSsbVetduatService_multipleCodes_exists() throws Exception {
        JsonNode expected = objectMapper.readTree(TestResultStrings.multipleExistsResult);
        when(vetduatRestRepository.callVetDuAt(anyString())).thenReturn(TestResultStrings.singleExistsResult);

        ssbVetduatService.productInformationForCodes("2,3").thenAccept(result ->
                resultNode.set(result));

        JsonNode actual = resultNode.get();
        assertEquals(expected, actual);
        assertEquals(2, actual.size());
    }
}
