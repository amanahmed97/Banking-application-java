import java.util.ArrayList;


public class SecurityAccount extends Account {
    private double stockBalance;
    private double profit;
    private double loss;
    private ArrayList<Stock> stockListOwned;

    public SecurityAccount(long accountId, int customerId) {
        super(AccountType.SECURITY, accountId, customerId);
        setLoss(0);
        setProfit(0);
        setStockBalance(0);
        stockListOwned = new ArrayList<Stock>();
    }

    public SecurityAccount(long accountId, int customerId, double stockBalance, double profit,
                           double loss, ArrayList<Stock> stockArrayList) {
        super(AccountType.SECURITY, accountId, customerId);
        setLoss(loss);
        setProfit(profit);
        setStockBalance(stockBalance);
        this.stockListOwned = new ArrayList<Stock>();
        this.stockListOwned = stockArrayList;
    }

    public boolean withdraw(CurrencyType currencyType, double amount) {
        /*
        Method used to withdraw money from stockBalance
        Checks for sufficient balance
         */

        if (stockBalance > amount) {
            stockBalance -= amount;
            return true;
        }


        return false;
    }

    public void deposit(CurrencyType currency, double amount) {
        /*
        Method used to deposit money to stockBalance
         */

        if (amount > 0) {
            stockBalance += amount;
        }

    }

    public double getStockBalance() {
        return stockBalance;
    }

    public void setStockBalance(double stockBalance) {
        this.stockBalance = stockBalance;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public ArrayList<Stock> getStockListOwned() {
        return stockListOwned;
    }


    public double getStockHoldingValue() {
        /*
        This method calculates the total value of stocks held in portfolio.
        This is done by looping through all the stocks.
        Then it is multiplied stock price by quantity to add to holding value.
         */

        updateStockPrices();

        double holding = 0;

        // Loop through the list of stocks owned
        for (Stock stock : stockListOwned) {
            // Calculate as price of stock times the quantity of the stock
            holding += stock.getStockPrice() * stock.getStockQuantity();
        }

        return holding;

    }

    public double getUnrealizedGain() {
        /*
        This method calculates the total increase in value of stocks held in portfolio,
        from their original buying price.
        This is done by looping through all the stocks.
        Then gain/loss of each stock is calculated and added up.
         */

        updateStockPrices();

        double unrealizedGains = 0;

        // Loop through the list of stocks owned
        for (Stock stock : stockListOwned) {
            // Calculate as current price of stock subtracted by buy price
            unrealizedGains += stock.getStockPrice() - stock.getStockBuyPrice();
        }

        return unrealizedGains;
    }

    //todo
    public double checkLoss() {
        return loss;
    }

    public boolean buyTrade(Stock stock, int quantity) {
        /*
        This method makes purchase of stocks to add in portfolio.
        Checks quantity of stock to be bought.
        Checks if sufficient balance available to make purchase.
        Check if stock already owned, if so increase quantity of owned stock.
        This is done by looping through all the stocks,
        if same stock id found, add it up.
         */

        updateStockPrices();

        // Check for sufficient balance against total purchase value.
        double purchaseValue;
        purchaseValue = stock.getStockPrice() * quantity;
        if (stockBalance < purchaseValue)
            return false;

        // Loop through the list of stocks owned
        // Check if stock already in portfolio
        for (Stock ownedStock : stockListOwned) {
            if (ownedStock.getStockId() == stock.getStockId()) {
                // If already owned make purchase and add quantity
                stockBalance -= purchaseValue;
                ownedStock.setStockQuantity(ownedStock.getStockQuantity() + quantity);
                return true;
            }
        }

        // If stock not owned, add to portfolio and make purchase
        Stock newStock = new Stock(stock.getStockId(), stock.getStockName(), stock.getStockPrice(), quantity);
        stockBalance -= purchaseValue;
        stockListOwned.add(newStock);

        return true;
    }

    //todo
    public double sellTrade(Stock stock, int quantity) {
        /*
        This method sells stocks in portfolio.
        Checks available quantity of stocks to be sold.
        This is done by looping through all the stocks,
        if same stock is found, subtract quantity from it.
        Checks for current price of the stock against buy price.
        Update stock balance and profit/loss.
        If stock balance is <=0 remove from owned stocks list.
         */

        updateStockPrices();

        // Check for sufficient quantity available for selling
        double sellValue;
        sellValue = stock.getStockPrice() * quantity;

        // Loop through the list of stocks owned
        // Check if stock in portfolio
        for (Stock ownedStock : stockListOwned) {
            if (ownedStock.getStockId() == stock.getStockId()) {
                // If sufficient stock quantity available sell it and add profits
                if (ownedStock.getStockQuantity() < quantity)
                    return 0;

                // Calculate profit/loss on trade
                double stockBuyPrice = ownedStock.getStockBuyPrice() * ownedStock.getStockQuantity();
                double tradeValue = sellValue - stockBuyPrice;
                if (tradeValue > 0) {
                    profit += tradeValue;
                } else {
                    loss += tradeValue;
                }

                // Sell the stock and add to stock balance
                ownedStock.setStockQuantity(ownedStock.getStockQuantity() - quantity);
                stockBalance += sellValue;

                // If stock quantity is <= 0, remove from owned stocks list
                if (ownedStock.getStockQuantity() <= 0) {
                    stockListOwned.remove(ownedStock);
                }

                return sellValue;
            }
        }

        // If stock not owned, return 0

        return 0;
    }

    public boolean updateStockPrices() {
        /*
        This method sets the current market price of stocks held in portfolio.
        It sets the price to be the same as the one in the stock market.
        This is done by looping through all the stocks.
        Then we get the market stock by is and set ou stock holding price..
         */

        // Loop through the list of stocks owned
        for (Stock stock : stockListOwned) {

            // Fetch the marketStock by its Id from the stock market
            Stock marketStock = StockMarket.getStockById(stock.getStockId());

            // Set the latest price of the stock in the portfolio
            if (marketStock != null) {
                stock.setStockPrice(marketStock.getStockPrice());
            }

        }

        return true;
    }

    //todo
    public void openPosition() {

    }
}
