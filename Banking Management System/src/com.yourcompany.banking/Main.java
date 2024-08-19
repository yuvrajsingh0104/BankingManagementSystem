import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingOperations operations = new BankingOperations(); // Assume BankingOperations class is defined correctly as previously discussed.

        while (true) {
            System.out.println("Choose an option:\n1. Create Account\n2. Deposit\n3. Withdraw\n4. Check Balance\n5. Exit");
            System.out.print("Enter choice (1-5): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Collect details and call createAccount
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Email: ");
                    String email = scanner.next();
                    System.out.print("Enter Password: ");
                    String password = scanner.next();
                    System.out.print("Enter Account Type: ");
                    String accountType = scanner.next();
                    System.out.print("Enter Initial Deposit: ");
                    double initialDeposit = scanner.nextDouble();

                    operations.createAccount(name, email, password, accountType, initialDeposit);
                    System.out.println("Account created successfully!");
                    break;

                case 2:
                    // Collect details and call deposit
                    System.out.print("Enter Account ID: ");
                    int depositAccountId = scanner.nextInt();
                    System.out.print("Enter Deposit Amount: ");
                    double depositAmount = scanner.nextDouble();

                    operations.deposit(depositAccountId, depositAmount);
                    System.out.println("Amount deposited successfully!");
                    break;

                case 3:
                    // Collect details and call withdraw
                    System.out.print("Enter Account ID: ");
                    int withdrawAccountId = scanner.nextInt();
                    System.out.print("Enter Withdrawal Amount: ");
                    double withdrawalAmount = scanner.nextDouble();

                    try {
                        operations.withdraw(withdrawAccountId, withdrawalAmount);
                        System.out.println("Amount withdrawn successfully!");
                    } catch (Exception e) {
                        System.out.println("Error during withdrawal: " + e.getMessage());
                    }
                    break;

                case 4:
                    // Call getBalance
                    System.out.print("Enter Account ID: ");
                    int balanceAccountId = scanner.nextInt();

                    double balance = operations.getBalance(balanceAccountId);
                    System.out.println("Current Balance: " + balance);
                    break;

                case 5:
                    // Exit the application
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
