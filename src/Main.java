import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class Main {
    private static String GAME_STATE_WON = "Вы выиграли!";
    private static String GAME_STATE_LOST = "Вы проиграли!";
    private static final int MAX_NUMBER_OF_MISTAKES = 6;
    private static int mistakes;
    private static String word;
    private static Random randomGenerator = new Random();
    private static ArrayList <String> dictionary = new ArrayList<>();
    private static String[] letters;
    private static String letter;
    private static StringBuilder mask = new StringBuilder();
    private static ArrayList <String> usedLetters = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        while (true) {
            startGameRound();
            startGameLoop();
        }
    }


    public static void createDictionary() throws IOException { // создание словаря
        File file = new File("dictionary.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
        scanner.close();
    }

    public static void startGameRound()  {
        boolean gameRoundIsNotStarted = true;
        Scanner scanner = new Scanner(System.in);
        while (gameRoundIsNotStarted) {
            System.out.println("Для начала игры введите 'Старт', для выхода - 'Стоп'");
            String input = scanner.next();
            switch (input) {
                case "Старт" -> gameRoundIsNotStarted = false;
                case "Стоп" -> System.exit(0);
                default -> System.out.println("Попробуйте снова");
            }
        }
    }

    public static void startGameLoop() throws IOException {
        createDictionary();
        generateRandomWord();
        mask = getMaskedWord();
        while (!GameIsEnded()) {
            System.out.println(mask);
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Введите букву");
            letter = keyboard.nextLine();
            if (!LetterisCorrect(letter)) {
                System.out.println("Можно ввести только одну букву!");
                continue;
            }
            if (!LetterisRussian(letter)) {
                System.out.println("Можно ввести только русскую букву!");
                continue;
            }
            if (LetterisEnteredBefore(letter)) {
                System.out.println("Вы уже использовали эту букву!");
                continue;
            }
            if (!LetterisRight(letter)) {
                addMistake();
            }

            printGallows();
            printNumberOfMistakes();

            if (mistakesLimitReached()) {
                System.out.println(GAME_STATE_LOST);
                printWord();
            }
            if (WordisUnlocked()) {
                System.out.println(GAME_STATE_WON);
                printWord();
            }
            if (GameIsEnded()) {
                for (int i = 0; i < usedLetters.size(); i++) {
                    usedLetters.remove(usedLetters.get(i));
                    i--;
                }
                mistakes = 0;
                break;
            }

        }
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

    public static void generateRandomWord() {
        int index = randomGenerator.nextInt(dictionary.size());
        word = dictionary.get(index);
    }

    public static void printWord() {
        System.out.println("Исходное слово: " + word);
    }


    public static void addMistake() {
        mistakes++;
    }

    public static ArrayList <Character> setLettersMasked() {
        ArrayList <Character> maskedLetters = new ArrayList<>();
       for (int i = 0; i < word.length(); i++) {
           maskedLetters.add('*');
       }
       return maskedLetters;
    }

    public static StringBuilder getMaskedWord() {
        StringBuilder maskedWord = new StringBuilder();
        ArrayList <Character> listOfMaskedletters = setLettersMasked();
        for (Character maskedLetter: listOfMaskedletters) {
            maskedWord.append(maskedLetter);
        }
        return maskedWord;
    }

    public static boolean LetterisCorrect(String letter) {
        return letter.length() == 1;
    }

    public static boolean LetterisRight(String letter) {
        boolean letterIsConsist = false;
        letters = word.split("");
        for (int i = 0; i < word.length(); i++) {
            if (letters[i].equals(letter)) {
                mask.setCharAt(i, letter.charAt(0));
                letterIsConsist = true;
            }
        }
        return letterIsConsist;
    }

    public static boolean LetterisRussian(String letter) {
        return Pattern.matches(".*\\p{InCyrillic}.*", letter);
    }

    public static boolean mistakesLimitReached() {
        return mistakes == MAX_NUMBER_OF_MISTAKES;
    }

    public static void printNumberOfMistakes() {
        System.out.println("Вы допустили " + mistakes + " ошибок");
    }

    public static boolean WordisUnlocked() {
        String unlockedWord = new String(mask);
        return unlockedWord.equals(word);
    }

    public static void addUserLetters() {
        usedLetters.add(letter);

    }

    public static boolean LetterisEnteredBefore(String letter) {
        if (usedLetters.contains(letter)) {
            return true;
        }
        addUserLetters();
        return false;
    }

    public static boolean GameIsEnded() {
        return (mistakesLimitReached() || WordisUnlocked());
    }
}

