package org.translation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new JSONTranslator(null);
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            String quit = "quit";
            if (quit.equals(country)) {
                break;
            }

            String language = promptForLanguage(translator, country);
            if (quit.equals(language)) {
                break;
            }

            String translation = translator.translate(country, language);
            if (translation != null) {
                System.out.println(country + ": " + translation);
            }
            else {
                System.out.println(country + ": No translation found");
            }

            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if ("quit".equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        Collections.sort(countries);
        System.out.println(countries);
        System.out.println("select a country from above:");
        for (String country : countries) {
            System.out.println(country);
        }
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languages = translator.getCountryLanguages(country);
        Collections.sort(languages);
        System.out.println("select a language from above:");
        for (String language : languages) {
            System.out.println(language);
        }

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
