import java.util.Scanner;

/**
 * Main.java
 * The user interface: a text menu loop that talks to the Portfolio.
 */
public class Main {

    // Reads an int safely so a typo doesn't crash the program.
    private static int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    // Same idea for decimals.
    private static double readDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number (like 125.50).");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== STOCK PORTFOLIO TRACKER ===");
        System.out.println("1. View Portfolio Status");
        System.out.println("2. Buy a Stock");
        System.out.println("3. Sell a Stock");
        System.out.println("4. Simulate Next Market Day");
        System.out.println("5. Exit Program");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Portfolio portfolio = new Portfolio(10000.00);
        boolean running = true;

        System.out.println("Welcome! You start with $10,000.00 in virtual cash.");

        while (running) {
            printMenu();
            int choice = readInt(input, "Choose an option: ");

            if (choice == 1) {
                portfolio.printPortfolio();

            } else if (choice == 2) {
                System.out.print("Ticker symbol (e.g. NVDA): ");
                String ticker = input.nextLine().trim();
                double price = readDouble(input, "Price per share: $");
                int shares = readInt(input, "Number of shares: ");

                if (ticker.isEmpty()) {
                    System.out.println("Ticker can't be empty.");
                } else if (portfolio.buyStock(ticker, price, shares)) {
                    System.out.printf("Bought %d shares of %s. Cash left: $%,.2f%n",
                            shares, ticker.toUpperCase(), portfolio.getCashBalance());
                } else {
                    System.out.println("Purchase failed: check your cash balance and inputs.");
                }

            } else if (choice == 3) {
                System.out.print("Ticker to sell: ");
                String ticker = input.nextLine().trim();
                int shares = readInt(input, "Number of shares: ");

                if (portfolio.sellStock(ticker, shares)) {
                    System.out.printf("Sold %d shares of %s. Cash: $%,.2f%n",
                            shares, ticker.toUpperCase(), portfolio.getCashBalance());
                } else {
                    System.out.println("Sale failed: do you own that many shares?");
                }

            } else if (choice == 4) {
                portfolio.updateMarket();
                System.out.println("Market day simulated. Prices have changed!");
                portfolio.printPortfolio();

            } else if (choice == 5) {
                System.out.printf("Final wealth: $%,.2f. Goodbye!%n",
                        portfolio.calculateTotalWealth());
                running = false;

            } else {
                System.out.println("Invalid option. Pick 1-5.");
            }
        }
        input.close();
    }
}
