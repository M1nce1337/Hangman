//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;
import java.io.*;


public class Main {
    private static String GAME_STATE_WON = "Вы выиграли!";
    private static String GAME_STATE_LOST = "Вы проиграли!";
    private static final int MAX_NUMBER_OF_MISTAKES = 6;
    private static int mistakes = 0;
    private static String word;
    private static ArrayList <Character> maskedLetters = new ArrayList<>();
    private static StringBuilder maskedWord = new StringBuilder();
    private static Random randomGenerator = new Random();
    private static ArrayList<String> dictionary = new ArrayList<>();
    private static String[] letters;
    private static ArrayList <String> listOfLetters = new ArrayList<>();
    private static String letter;
    private static HashSet <Character> usedLetters = new HashSet<>();


    public static void main(String[] args) throws IOException {
        createDictionary();
        printWord();
        setMaskedLetters();
        printMaskedWord();
        setInputLetter();
        isLetterCorrect(letter);
        printGameInfo();
        countMistakes();
        printGallows();
    }

    public static void createDictionary() throws IOException {
        File file = new File("dictionary.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
        scanner.close();
    }

    public static void gameLoop() {
        // while (gameNotOver)
        // startGameRound();
        // drawHangman();
        // generateWord();
        // getInputLetter();
        // createDictionary();
        // printStartInfo();
    }

    public static void printGallows() {
        String gallowsState;
        switch (mistakes) {
            case 1 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "==========================";
            case 2 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||                 |\n" +
                    "  ||                 |\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "==========================";
            case 3 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||                /|\n" +
                    "  ||                 |\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "==========================";
            case 4 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||                /|\\\n" +
                    "  ||                 |\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "==========================";
            case 5 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||                /|\\\n" +
                    "  ||                 |\n" +
                    "  ||                /\n" +
                    "  ||\n" +
                    "==========================";
            case 6 -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||                (ツ)\n" +
                    "  ||                /|\\\n" +
                    "  ||                 |\n" +
                    "  ||                / \\\n" +
                    "  ||\n" +
                    "==========================";
            default -> gallowsState = "=====================|\n" +
                    "  ||                 |\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "  ||\n" +
                    "==========================";
        }
        System.out.println(gallowsState);

    }

    public static String getRandomWord() {
        int index = randomGenerator.nextInt(dictionary.size());
        word = dictionary.get(index);
        return word;
    }

    public static void printWord() {
        System.out.println("Исходное слово: " + getRandomWord());
    }

    public static void setInputLetter() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Введите букву");
        letter = keyboard.nextLine();
    }

    public static void countMistakes() {
        letters = word.split("");
        for (int i = 0; i < letters.length; i++) {
            listOfLetters.add(letters[i]);
        }
        if (!listOfLetters.contains(letter)) {
            mistakes++;
        }
        System.out.println(mistakes);
    }

    public static void setMaskedLetters() {
       for (int i = 0; i < word.length(); i++) {
           maskedLetters.add('*');
       }
    }

    public static StringBuilder getMaskedWord() {
        for (Character maskedLetter: maskedLetters) {
            maskedWord.append(maskedLetter);
        }
        return maskedWord;
    }

    public static void printMaskedWord() {
        System.out.println(getMaskedWord());
    }

    public static boolean isLetterCorrect(String letter) {
        return letter.length() == 1;
    }

    public static void printGameInfo() {
        if (!isLetterCorrect(letter)) {
            System.out.println("Можно ввести только одну букву!");
        }
    }
}

