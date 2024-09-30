package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private Map<String, Map<String, String>> tToJ;
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        tToJ = new HashMap<>();
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String alpha2Code = "alpha2".getString(countryObject);

                Map<String, String> languageMap = new HashMap<>();

                for (String key : countryObject.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        languageMap.put(key, countryObject.getString(key));
                    }
                }

                tToJ.put(alpha2Code, languageMap);
            }
        }

        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        if (tToJ.containsKey(country)) {
            return new ArrayList<>(tToJ.get(country).keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(tToJ.keySet());

    }

    @Override
    public String translate(String country, String language) {
        if (tToJ.containsKey(country)) {
            Map<String, String> translations = tToJ.get(country);
            if (translations.containsKey(language)) {
                return translations.get(language);
            }
        }
        return null;
    }
}
