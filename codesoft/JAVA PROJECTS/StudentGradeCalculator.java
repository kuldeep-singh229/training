import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = readPositiveInt(sc, "Enter number of subjects: ");

            int total = 0;
            for (int i = 1; i <= n; i++) {
                int marks = readIntInRange(sc, "Enter marks of Subject " + i + ": ", 0, 100);
                total += marks;
            }

            double average = (double) total / n;
            double percentage = average;
            char grade = calculateGrade(percentage);

            System.out.println("\n----- RESULT -----");
            System.out.println("Total Marks = " + total);
            System.out.printf("Average Marks = %.2f%n", average);
            System.out.printf("Percentage = %.2f%%%n", percentage);
            System.out.println("Grade = " + grade);
        }
    }

    private static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value <= 0) {
                    System.out.println("Please enter a positive number.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static int readIntInRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static char calculateGrade(double percentage) {
        if (percentage >= 90) {
            return 'A';
        } else if (percentage >= 80) {
            return 'B';
        } else if (percentage >= 70) {
            return 'C';
        } else if (percentage >= 60) {
            return 'D';
        }
        return 'F';
    }
}
