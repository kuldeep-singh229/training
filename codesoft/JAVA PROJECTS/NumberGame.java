import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_ATTEMPTS = 7;
    private static final int POINTS_PER_ATTEMPT = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int roundsPlayed = 0;
        int roundsWon = 0;
        int totalScore = 0;
        int bestScore = 0;

        System.out.println("===== NUMBER GUESSING GAME =====");

        boolean playAgain;
        do {
            roundsPlayed++;
            int roundScore = playRound(sc, random, roundsPlayed);

            if (roundScore > 0) {
                roundsWon++;
                totalScore += roundScore;
                bestScore = Math.max(bestScore, roundScore);
            }

            playAgain = askYesNo(sc, "Do you want to play another round? (Y/N): ");
        } while (playAgain);

        printSummary(roundsPlayed, roundsWon, totalScore, bestScore);
        sc.close();
    }

    private static int playRound(Scanner sc, Random random, int roundNumber) {
        int secretNumber = random.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER;

        System.out.println("\nRound " + roundNumber);
        System.out.println("Guess the number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts.");

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            int guess = readInt(sc, "Attempt " + attempt + ": ", MIN_NUMBER, MAX_NUMBER);

            if (guess == secretNumber) {
                int score = (MAX_ATTEMPTS - attempt + 1) * POINTS_PER_ATTEMPT;
                System.out.println("Congratulations! You guessed the correct number.");
                System.out.println("Score earned: " + score);
                return score;
            }

            if (guess < secretNumber) {
                System.out.println("Too low! Try again.");
            } else {
                System.out.println("Too high! Try again.");
            }
        }

        System.out.println("Sorry! You've used all attempts.");
        System.out.println("The correct number was: " + secretNumber);
        return 0;
    }

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a whole number.");
                sc.next();
                continue;
            }

            int value = sc.nextInt();
            sc.nextLine();

            if (value < min || value > max) {
                System.out.println("Enter a number between " + min + " and " + max + ".");
                continue;
            }

            return value;
        }
    }

    private static boolean askYesNo(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = sc.nextLine().trim().toLowerCase();

            if (answer.isEmpty()) {
                continue;
            }

            char firstChar = answer.charAt(0);
            if (firstChar == 'y') {
                return true;
            }
            if (firstChar == 'n') {
                return false;
            }

            System.out.println("Please answer 'Y' or 'N'.");
        }
    }

    private static void printSummary(int roundsPlayed, int roundsWon, int totalScore, int bestScore) {
        System.out.println("\n===== GAME OVER =====");
        System.out.println("Rounds Played : " + roundsPlayed);
        System.out.println("Rounds Won    : " + roundsWon);
        System.out.println("Total Score   : " + totalScore);
        System.out.println("Best Round    : " + (bestScore > 0 ? bestScore : 0));
    }
}
