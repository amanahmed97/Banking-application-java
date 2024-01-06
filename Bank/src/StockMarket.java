import java.util.*;

public class StockMarket {

    // Stock Market listed stocks are publicly available information
    public static ArrayList<Stock> stockMarketList;

    public StockMarket(){
        stockMarketList = new ArrayList<Stock>();
    }

    public ArrayList<Stock> getStockMarketList(){
        /*
        Method returns list of stock in stock market
        */

        return stockMarketList;
    }

    public static boolean addStockToMarket(Stock stock){
        /*
        This method takes a stock as argument.
        Loop through all stock in stock market list.
        Check if stock already present in stockMarket,
        If not, add it.
        */

        // Loop through the stocks list in stock market
        for (Stock marketStock: stockMarketList) {

            // If stock already present, return false
            if (marketStock.getStockId() == stock.getStockId()) {
                return false;
            }
        }

        // Add the new stock to the stock market
        stockMarketList.add(stock);

        return true;
    }

    public static boolean removeStockFromMarket(Stock stock){
        /*
        This method takes a stock as argument.
        Loop through all stock in stock market list.
        if stock present in stockMarket, remove it.
        */

        // Loop through the stocks list in stock market
        for (Stock marketStock: stockMarketList) {

            // If stock present, remove it and return true
            if (marketStock.getStockId() == stock.getStockId()) {

                stockMarketList.remove(marketStock);
                return true;
            }
        }


        return false;
    }

    public static void simulateStockMarket() {
        /*
        This method is used to simulate the change in the stock market
        It is done is randomizing the stock's price.
        Loop through all the stocks and simulate their price changes.
         */

        // Loop through the stocks list in stock market
        for (Stock marketStock: stockMarketList) {

            // For each stock simulate its price change
            marketStock.simulateStockPrice();
        }

    }

    public static Stock getStockById(int stockId){
        /*
        This method is used to fetch the stock in the stock market
        Loop through all the stocks and return with matching id.
         */

        // Loop through the stocks list in stock market
        for (Stock marketStock: stockMarketList) {

            // If the stock Id matches, return it
            if (marketStock.getStockId() == stockId){
                return marketStock;
            }
        }

        // If stock not found, return null
        return null;

    }

    public static Stock getStockByName(String stockName){
        /*
        This method is used to fetch the stock in the stock market
        Loop through all the stocks and return with matching id.
         */

        // Loop through the stocks list in stock market
        for (Stock marketStock: stockMarketList) {

            // If the stock Id matches, return it
            if (marketStock.getStockName().equals(stockName)){
                return marketStock;
            }
        }

        // If stock not found, return null
        return null;

    }


}
