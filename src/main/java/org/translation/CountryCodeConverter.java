package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class provides the service of converting country codes to their names.
 */

public class CountryCodeConverter {

    private final Map<String, String> cToN;
    private final Map<String, String> nToC;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resource's folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        cToN = new HashMap<>();
        nToC = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (String line : lines) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    cToN.put(parts[0], parts[1]);
                    nToC.put(parts[0], parts[1]);
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        String x = cToN.get(code);
        if (x != null) {
            return x;
        }
        else {
            return code;
        }
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        String x = nToC.get(country);
        if (x != null) {
            return x;
        }
        else {
            return country;
        }
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return cToN.size();
    }
}
