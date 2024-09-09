import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

public class Main {
    private static final String GAME_STATE_WON = "Вы выиграли!";
    private static final String GAME_STATE_LOST = "Вы проиграли!";
    private static final String COMMAND_START = "Старт";
    private static final String COMMAND_STOP = "Стоп";
    private static final int MAX_NUMBER_OF_MISTAKES = 6;
    private static int mistakes = 0;
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


    public static void createDictionary() throws IOException {
        FileReader fr = new FileReader("src/dictionary/words.txt");
        Scanner scanner = new Scanner(fr);
        while (scanner.hasNext()) {
            dictionary.add(scanner.next());
        }
        scanner.close();
    }

    public static void startGameRound() {
        boolean isGameRoundNotStarted = true;
        Scanner scanner = new Scanner(System.in);
        while (isGameRoundNotStarted) {
            System.out.printf("Для начала игры введите '%s', для выхода - '%s' \n", COMMAND_START, COMMAND_STOP);
            String input = scanner.next();
            if (input.equalsIgnoreCase(COMMAND_START)) {
                isGameRoundNotStarted = false;
            }
            else if (input.equalsIgnoreCase(COMMAND_STOP)) {
                System.exit(0);
            }
            else {
                System.out.println("Попробуйте снова");
            }
        }
    }

    public static void startGameLoop() throws IOException {
        createDictionary();
        generateRandomWord();
        mask = getMaskedWord();
        GallowsDrawer hangmanPrinter = new GallowsDrawer();
        Scanner keyboard = new Scanner(System.in);
        while (!isGameEnded()) {
            System.out.println(hangmanPrinter.getPicture(mistakes));
            System.out.println(mask);
            System.out.println("Введите букву");
            letter = keyboard.nextLine();
            if (!isLetterCorrect(letter)) {
                System.out.println("Можно ввести только одну букву!");
                continue;
            }
            if (!isLetterRussian(letter)) {
                System.out.println("Можно ввести только русскую букву!");
                continue;
            }
            if (isLetterEnteredBefore(letter) || isLetterSame(letter)) {
                System.out.println("Вы уже использовали эту букву!");
                continue;
            } else {
                addUserLetters();
            }
            if (!isLetterRight(letter)) {
                addMistake();
            }
            printUsedLetters();
            System.out.println();
            printNumberOfMistakes();

            if (mistakesLimitReached()) {
                System.out.println(GAME_STATE_LOST);
                printWord();
            }
            if (isWordUnlocked()) {
                System.out.println(GAME_STATE_WON);
                printWord();
            }
            if (isGameEnded()) {
                for (int i = 0; i < usedLetters.size(); i++) {
                    usedLetters.remove(usedLetters.get(i));
                    i--;
                }
                mistakes = 0;
                break;
            }
        }
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

    public static boolean isLetterCorrect(String letter) {
        return letter.length() == 1;
    }

    public static boolean isLetterRight(String letter) {
        boolean letterIsConsist = false;
        letters = word.split("");
        for (int i = 0; i < word.length(); i++) {
            if (letters[i].equalsIgnoreCase(letter)) {
                mask.setCharAt(i, letter.charAt(0));
                letterIsConsist = true;
            }
        }
        return letterIsConsist;
    }

    public static boolean isLetterRussian(String letter) {
        return Pattern.matches(".*\\p{InCyrillic}.*", letter);
    }

    public static boolean mistakesLimitReached() {
        return mistakes == MAX_NUMBER_OF_MISTAKES;
    }

    public static void printNumberOfMistakes() {
        System.out.println("Вы допустили " + mistakes + " ошибок");
    }

    public static boolean isWordUnlocked() {
        String unlockedWord = new String(mask);
        return unlockedWord.equals(word);
    }

    public static void addUserLetters() {
        usedLetters.add(letter);
    }

    public static boolean isLetterEnteredBefore(String letter) {
        return usedLetters.contains(letter);
    }

    public static boolean isGameEnded() {
        return (mistakesLimitReached() || isWordUnlocked());
    }

    public static void printUsedLetters() {
        System.out.print("Использованные буквы: ");
        for (int i = 0; i < usedLetters.size(); i++) {
            System.out.print(usedLetters.get(i) + " ");
        }
    }

    public static boolean isLetterSame(String letter) {
        boolean isLetterNotDifferent = false;
        for (int i = 0; i < usedLetters.size(); i++) {
            if (usedLetters.get(i).equalsIgnoreCase(letter)) {
                isLetterNotDifferent = true;
            }
        }
        return isLetterNotDifferent;
    }
}

