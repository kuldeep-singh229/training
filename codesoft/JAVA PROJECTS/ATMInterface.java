import java.util.Scanner;

// Class to represent Bank Account
class BankAccount {
    private double balance;

    // Constructor
    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    // Deposit method
    public int deposit(double amount) {
        if (amount <= 0) {
            return -1; // invalid amount
        }

        balance += amount;
        return 0; // success
    }

    // Withdraw method
    public int withdraw(double amount) {
        if (amount <= 0) {
            return -1; // invalid amount
        }

        if (amount > balance) {
            return -2; // insufficient balance
        }

        balance -= amount;
        return 0; // success
    }

    // Check balance method
    public double getBalance() {
        return balance;
    }
}

// Class to represent ATM
class ATM {
    private BankAccount account;

    // Constructor
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Display ATM menu
    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            choice = readInt(sc, "Enter your choice: ");

            switch (choice) {
                case 1:
                    System.out.println("Current Balance: ₹" + account.getBalance());
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmount = sc.nextDouble();
                    int depositStatus = account.deposit(depositAmount);

                    if (depositStatus == 0) {
                        System.out.println("₹" + depositAmount + " deposited successfully.");
                    } else {
                        System.out.println("Invalid amount!");
                    }
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmount = sc.nextDouble();
                    int withdrawStatus = account.withdraw(withdrawAmount);

                    if (withdrawStatus == 0) {
                        System.out.println("₹" + withdrawAmount + " withdrawn successfully.");
                    } else if (withdrawStatus == -1) {
                        System.out.println("Invalid amount!");
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                    break;

                case 4:
                    System.out.println("Thank you for using the ATM!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 4);

        sc.close();
    }

    private int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid integer.");
                sc.nextLine();
                continue;
            }
            int value = sc.nextInt();
            if (value < 0) {
                System.out.println("Please enter a non-negative integer.");
                continue;
            }
            return value;
        }
    }
}

// Main class
public class ATMInterface {
    public static void main(String[] args) {
        BankAccount userAccount = new BankAccount(10000); // Initial balance
        ATM atm = new ATM(userAccount);
        atm.showMenu();
    }
}