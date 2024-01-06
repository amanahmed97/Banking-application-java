import java.util.*;

public class Stock {
    /*
    Class for representing the stocks and their values in the stock market.
    Methods for get-set and simulating stock price.
     */
    private int stockId;
    private String stockName;
    private double stockPrice;
    private double stockBuyPrice;
    private int stockQuantity;

    public Stock(int stockId, String stockName, double stockPrice, int stockQuantity){
        setStockId(stockId);
        setStockName(stockName);
        setStockPrice(stockPrice);
        setStockBuyPrice(stockPrice);
        setStockQuantity(stockQuantity);
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public double getStockBuyPrice() {
        return stockBuyPrice;
    }

    public void setStockBuyPrice(double stockBuyPrice) {
        this.stockBuyPrice = stockBuyPrice;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void simulateStockPrice() {
        /*
        This method is used to simulate the change in the stock's price.
        This is done by generating a random swing percentage.
        Then it is randomly decided if the change is an increase or decrease in price.
         */

        // Use a random generator to get a number % for increase or decrease
        Random random = new Random();

        // Generate number for change percentage
        double swing = random.nextDouble()*15;

        // Generate the change is an increase or decrease in price
        int change = random.nextInt(2)+1;

        // If change generated is 1, increase the price
        // If change generated is 2, decrease the price
        if ( change == 1 ){
            stockPrice += stockPrice*swing/100;
        } else{
            stockPrice -= stockPrice*swing/100;
            // If decrease is reduces stock price below 0, set it to 1
            if (stockPrice <= 0)
                stockPrice = 1;
        }

    }

    public boolean addStockQuantity(int addQuantity) {
        /*
        This method is used to add purchased quantity to stock holding.
         */

        stockQuantity += addQuantity;

        return true;

    }

    public boolean subtractStockQuantity(int subtractQuantity) {
        /*
        This method is used to subtract sold quantity from stock holding.
         */

        // Check if required quantity of stock exists
        if (stockQuantity < subtractQuantity){
            return false;
        }

        stockQuantity -= subtractQuantity;

        return true;

    }


}
