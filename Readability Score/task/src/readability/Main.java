package readability;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        int numberOfCharacters = 0;
        int numberOfWords = 0;
        int numberOfSentences = 0;
        ArrayList<String> text = new ArrayList<>();

        File file = new File(args[0]);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                text.add(word);
                numberOfWords++;
                numberOfCharacters += word.length();
                if (word.matches("\\w+[!\\?\\.\\n]") || !scanner.hasNext()) {
                    numberOfSentences++;
                }
            }
        } catch (IOException e) {
            System.out.println("Wrong file");
        }
        int numberOfSyllables = calculateNumberOfSyllablesAndPolysyllables(text)[0];
        int numberOfPolysyllables = calculateNumberOfSyllablesAndPolysyllables(text)[1];

        System.out.println("Words: " + numberOfWords);
        System.out.println("Sentences: " + numberOfSentences);
        System.out.println("Characters: " + numberOfCharacters);
        System.out.println("Syllables: " + numberOfSyllables);
        System.out.println("Polysyllables: " + numberOfPolysyllables);

        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        switch (new Scanner(System.in).nextLine()) {
            case "ARI":
                calculateARI(numberOfWords, numberOfSentences, numberOfCharacters);
                break;
            case "FK":
                calculateFleschKincaidScore(numberOfWords, numberOfSentences, numberOfSyllables);
                break;
            case "SMOG":
                calculateSMOGIndex(numberOfSentences, numberOfPolysyllables);
                break;
            case "CL":
                calculateColemanLiauIndex(numberOfWords, numberOfSentences, numberOfCharacters);
                break;
            case "all":
                int average = 0;
                average += calculateARI(numberOfWords, numberOfSentences, numberOfCharacters);
                average += calculateFleschKincaidScore(numberOfWords, numberOfSentences, numberOfSyllables);
                average += calculateSMOGIndex(numberOfSentences, numberOfPolysyllables);
                average += calculateColemanLiauIndex(numberOfWords, numberOfSentences, numberOfCharacters);
                System.out.println();
                System.out.printf("\nThis text should be understood in average by %.2f year olds.", (double) average / 4);
                break;
            default:
                System.out.println("Wrong command.");
                break;
        }
    }

    public static int[] calculateNumberOfSyllablesAndPolysyllables(ArrayList<String> text) {
        String vowels = "aeiouyAEIOUY";
        int[] numberOfSyllablesAndPolysyllables = new int[2];

        for (String word : text) {
            int countOfVowels = 0;
            int vowelsInRow = 0;
            if (!word.matches("\\W*[0-9]*\\W*[0-9]*")) {
                for (int i = 0; i < word.length(); i++) {
                    if (vowels.contains(Character.toString(word.charAt(i))) && vowelsInRow < 1) {
                        countOfVowels++;
                        vowelsInRow++;
                    } else {
                        vowelsInRow = 0;
                    }
                }
                if (word.matches("\\w+[eE][!\\?\\.\\n]*")) {
                    countOfVowels--;
                }

                if (countOfVowels <= 0) {
                    countOfVowels = 1;
                }

                numberOfSyllablesAndPolysyllables[0] += countOfVowels;

                if (countOfVowels > 2) {
                    numberOfSyllablesAndPolysyllables[1]++;
                }
            }
        }
        return numberOfSyllablesAndPolysyllables;
    }

    public static int calculateARI(int words, int sentences, int characters) {
        double score = 4.71 * (double) characters / words + 0.5 * (double) words / sentences - 21.43;
        System.out.println();
        System.out.printf("Automated Readability Index: %.2f", score);
        System.out.printf(" (about %d year olds).", matchAgeTable(score));
        return matchAgeTable(score);
    }

    public static int calculateFleschKincaidScore(int words, int sentences, int syllables) {
        double score = 0.39 * (double) words / sentences + 11.8 * (double) syllables / words - 15.59;
        System.out.println();
        System.out.printf("Flesch–Kincaid readability tests: %.2f", score);
        System.out.printf(" (about %d year olds).", matchAgeTable(score));
        return matchAgeTable(score);
    }

    public static int calculateSMOGIndex(int sentences, int polysyllables) {
        double score = 1.043 * Math.sqrt((double) polysyllables * 30 / (double) sentences) + 3.1291;
        System.out.println();
        System.out.printf("Simple Measure of Gobbledygook: %.2f", score);
        System.out.printf(" (about %d year olds).", matchAgeTable(score));
        return matchAgeTable(score);
    }

    public static int calculateColemanLiauIndex(int words, int sentences, int characters) {
        double l = (double) characters / words * 100;
        double s = (double) sentences / words * 100;
        double score = 0.0588 * l - 0.296 * s - 15.8;
        System.out.println();
        System.out.printf("Coleman–Liau index: %.2f", score);
        System.out.printf(" (about %d year olds).", matchAgeTable(score));
        return matchAgeTable(score);
    }

    public static int matchAgeTable(double score) {
        switch ((int) Math.round(score)) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            case 13:
                return 24;
            case 14:
                return 25;
            default:
                return 0;
        }
    }
}