import java.util.ArrayList;
import java.util.Random;

/**
 * Portfolio.java
 * The engine: manages all stocks, the cash balance, and the market simulation.
 */
public class Portfolio {
    private ArrayList<Stock> myStocks;
    private double cashBalance;
    private double startingCash;
    private Random rand;
    private int day;

    public Portfolio(double startingCash) {
        this.myStocks = new ArrayList<Stock>();
        this.cashBalance = startingCash;
        this.startingCash = startingCash;
        this.rand = new Random();
        this.day = 1;
    }

    public double getCashBalance() { return cashBalance; }
    public int getDay()            { return day; }

    /** Linear search through the ArrayList. Returns null if not found. */
    private Stock findStock(String ticker) {
        for (int i = 0; i < myStocks.size(); i++) {
            if (myStocks.get(i).getTicker().equalsIgnoreCase(ticker)) {
                return myStocks.get(i);
            }
        }
        return null;
    }

    /**
     * Buys shares if we have enough cash.
     * @return true if the purchase worked, false otherwise
     */
    public boolean buyStock(String ticker, double price, int shares) {
        if (price <= 0 || shares <= 0) {
            return false;
        }
        double cost = price * shares;
        if (cost > cashBalance) {
            return false; // not enough cash
        }

        Stock existing = findStock(ticker);
        if (existing != null) {
            existing.addShares(shares, price);
        } else {
            myStocks.add(new Stock(ticker, price, shares));
        }
        cashBalance -= cost;
        return true;
    }

    /**
     * Sells shares at the current market price.
     * @return true if the sale worked, false otherwise
     */
    public boolean sellStock(String ticker, int shares) {
        Stock s = findStock(ticker);
        if (s == null || shares <= 0 || shares > s.getSharesOwned()) {
            return false;
        }
        cashBalance += s.getCurrentPrice() * shares;
        s.setSharesOwned(s.getSharesOwned() - shares);

        if (s.getSharesOwned() == 0) {
            myStocks.remove(s); // sold everything, drop the position
        }
        return true;
    }

    /**
     * Simulates one market day. Each stock moves by a random percent
     * between -5% and +5%. Prices can never drop below $0.01.
     */
    public void updateMarket() {
        for (Stock s : myStocks) {
            double percentChange = (rand.nextDouble() * 10.0) - 5.0; // -5.0 to +5.0
            double newPrice = s.getCurrentPrice() * (1 + percentChange / 100.0);
            if (newPrice < 0.01) {
                newPrice = 0.01;
            }
            s.setCurrentPrice(newPrice);
        }
        day++;
    }

    /** Stocks' market value plus leftover cash. */
    public double calculateTotalWealth() {
        double total = cashBalance;
        for (Stock s : myStocks) {
            total += s.getMarketValue();
        }
        return total;
    }

    /** Prints every holding plus a summary. */
    public void printPortfolio() {
        System.out.println("\n========== PORTFOLIO - DAY " + day + " ==========");
        if (myStocks.isEmpty()) {
            System.out.println("You don't own any stocks yet.");
        } else {
            for (Stock s : myStocks) {
                System.out.println(s);
            }
        }
        double wealth = calculateTotalWealth();
        double overallReturn = ((wealth - startingCash) / startingCash) * 100.0;
        System.out.println("-------------------------------------------");
        System.out.printf("Cash Balance:  $%,.2f%n", cashBalance);
        System.out.printf("Total Wealth:  $%,.2f%n", wealth);
        System.out.printf("Overall Return: %+.2f%%%n", overallReturn);
        System.out.println("===========================================");
    }
}
