package no.ssb.api.ssbvetduatapi.util;

public class ResultStrings {
    public static String emptyResult(long gtin, long epdNr, long gln, String message) {
        return "[{" +
                "\"epdNr\":" + epdNr + ", " +
                "\"produktnavn\":\"" + message + "\", " +
                "\"gtin\":" + gtin + ", " +
                "\"gln\":" + gln +
                "}]";
    }

    public static String emptyResult(String codeType, String code, String message) {
        return emptyResult(
                codeType.equalsIgnoreCase("gtin") ? Long.parseLong(code) : -1,
                codeType.equalsIgnoreCase("epdNr") ? Long.parseLong(code) : -1,
                codeType.equalsIgnoreCase("gln") ? Long.parseLong(code) : -1,
                message);
    }

    public static final String singleExistsResult = "[\n" +
            "  {\n" +
            "    \"epdNr\": 4608006,\n" +
            "    \"produktnavn\": \"Havrekli 950g bx Møllerens\",\n" +
            "    \"opprinnelsesland\": \"Norge\",\n" +
            "    \"gtin\": 7020655840885,\n" +
            "    \"gln\": 7080000757466,\n" +
            "    \"firmaNavn\": \"NORGESMØLLENE AS\",\n" +
            "    \"varegruppenavn\": \"Mel - kornblanding/frokostblanding (tørrvare)\",\n" +
            "    \"minimumsTemperaturCelcius\": 4,\n" +
            "    \"maksimumsTemperaturCelcius\": 25,\n" +
            "    \"ingredienser\": \"HAVREKLI\",\n" +
            "    \"bildeUrl\": \"https://vetduatapi-test.tradesolution.no/api/images/Medium/7020655840885/png\",\n" +
            "    \"mengde\": \"950\",\n" +
            "    \"mengdeType\": \"gram\",\n" +
            "    \"merkeordninger\": [\n" +
            "      \"Nøkkelhullet\"\n" +
            "    ],\n" +
            "    \"deklarasjoner\": [\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Energi kj pr 100 g/ml\",\n" +
            "        \"verdi\": \"1428.0000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Energi kcal pr 100 g/ml\",\n" +
            "        \"verdi\": \"340.0000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Fett (totalt) pr 100 g/ml\",\n" +
            "        \"verdi\": \"7.5000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Mettede fettsyrer pr 100 g/ml\",\n" +
            "        \"verdi\": \"1.6000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Enumettede fettsyrer pr 100 g/ml\",\n" +
            "        \"verdi\": \"2.7000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Flerumettede fettsyrer pr 100 g/ml\",\n" +
            "        \"verdi\": \"2.7000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Karbohydrater pr 100 g/ml\",\n" +
            "        \"verdi\": \"47.5000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Sukkerarter pr 100 g/ml\",\n" +
            "        \"verdi\": \"3.1000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Kostfiber pr 100 g/ml\",\n" +
            "        \"verdi\": \"15.0000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Protein pr 100 g/ml\",\n" +
            "        \"verdi\": \"13.2000\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"deklarasjon\": \"Salt pr 100 g/ml\",\n" +
            "        \"verdi\": \"0.0000\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"allergener\": [\n" +
            "      {\n" +
            "        \"allergen\": \"Gluten\",\n" +
            "        \"verdi\": \"Inneholder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Havre gluten\",\n" +
            "        \"verdi\": \"Inneholder\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Hvete gluten\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Spelt gluten\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Khorasanhvete gluten\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Rug gluten\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Bygg gluten\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Skalldyr\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Egg\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Fisk\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Peanøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Soya\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Melk\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Nøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Mandler\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Hasselnøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Valnøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Kasjunøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Pekannøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Paranøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Pistasienøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Macademiannøtter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Selleri\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Sennep\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Sesamfrø\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Svoveldioksid eller sulfitter\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Lupiner\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"allergen\": \"Bløtdyr\",\n" +
            "        \"verdi\": \"Inneholder ikke\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"sistEndret\": \"2018-09-03T07:30:59.93+00:00\"\n" +
            "  }\n" +
            "]";

    public static final String singleNotExistsResult = "[\n" +
            "  {\n" +
            "    \"epdNr\": -1,\n" +
            "    \"produktnavn\": \"Finner ikke hos vetduat\",\n" +
            "    \"gtin\": 2,\n" +
            "    \"gln\": -1\n" +
            "  }\n" +
            "]";



}
