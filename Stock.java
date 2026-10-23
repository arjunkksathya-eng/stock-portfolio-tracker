/*represents one stock position in the portfolio.
 */
public class Stock {
    private String ticker;
    private double purchasePrice;   // average price paid per share
    private double currentPrice;    // changes as the market moves
    private int sharesOwned;

    public Stock(String ticker, double purchasePrice, int sharesOwned) {
        this.ticker = ticker.toUpperCase();
        this.purchasePrice = purchasePrice;
        this.currentPrice = purchasePrice; // starts at the price we paid
        this.sharesOwned = sharesOwned;
    }

    // ---------- Getters ----------
    public String getTicker()         { return ticker; }
    public double getPurchasePrice()  { return purchasePrice; }
    public double getCurrentPrice()   { return currentPrice; }
    public int getSharesOwned()       { return sharesOwned; }

    // ---------- Setters ----------
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setSharesOwned(int sharesOwned) {
        this.sharesOwned = sharesOwned;
    }

    /**
     * Buying more of a stock we already own changes our average cost.
     * New average = (total money spent) / (total shares)
     */
    public void addShares(int newShares, double price) {
        double totalSpent = (purchasePrice * sharesOwned) + (price * newShares);
        sharesOwned += newShares;
        purchasePrice = totalSpent / sharesOwned;
    }

    /** Total value of this position right now. */
    public double getMarketValue() {
        return currentPrice * sharesOwned;
    }

    /** Percentage gain or loss: ((current - purchase) / purchase) * 100 */
    public double getReturnOnInvestment() {
        return ((currentPrice - purchasePrice) / purchasePrice) * 100.0;
    }

    @Override
    public String toString() {
        return String.format("%-6s | Shares: %-5d | Bought: $%8.2f | Now: $%8.2f | Value: $%10.2f | ROI: %+7.2f%%",
                ticker, sharesOwned, purchasePrice, currentPrice,
                getMarketValue(), getReturnOnInvestment());
    }
}
