import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class StockGUI extends JFrame {

    private JPanel Stock;
    private JTextArea holdStocks;
    private JTextArea stockList;
    private JTextField stockName;
    private JButton buyButton;
    private JButton sellButton;
    private JLabel stockAmount;
    private JTextField amount;
    private JButton cancelButton;
    private JTextField accountBalanceTextField;
    private JTextField textField1;

    private int getAmount(){
        return Integer.valueOf(amount.getText());
    }
    private String getStockName() { return stockName.getText();}

    public StockGUI(int user) {
        // Simulate the stock market price movements
        StockMarket.simulateStockMarket();

        accountBalanceTextField.setText("0.0");
        textField1.setText("0.0");

        setContentPane(Stock);
        setTitle("Stock");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        SecurityAccount stockAccount = getStockAccount(user);
        ArrayList<Stock> stockHoldList;


        if(stockAccount != null) {
            // Loop through list of stock held in portfolio
            for (Stock stock: stockAccount.getStockListOwned()){
                holdStocks.append(stock.getStockName()+" :: Quantity: "+stock.getStockQuantity()
                        +" BuyPrice: "+stock.getStockBuyPrice()+"\n\n");
            }

            // Set stock account balance
            accountBalanceTextField.setText(String.valueOf(stockAccount.getStockBalance()));
            // Set stock account holding portfolio value
            textField1.setText(String.valueOf(stockAccount.getStockHoldingValue()));
        }
        else{
            JOptionPane.showMessageDialog(Stock, "Please create a stock account");
            dispose();
        }

        // Get list of stocks available in Stock Market
        // Add it to the list
        for (Stock stock : StockMarket.stockMarketList){
            stockList.append(stock.getStockName()+" :: Price: "+stock.getStockPrice()+"\n\n");
        }

        amount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE)) {
                    getToolkit().beep();
                    e.consume();
                }
                super.keyTyped(e);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SecurityAccount stockAccount = getStockAccount(user);

                if(stockName.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Stock, "Please enter the stock name");
                }
                else {
                    int flag = 0;

                    // Get the marketStock form Stock Market listed stocks
                    Stock marketStock = StockMarket.getStockByName(getStockName());
                    // Check if valid stock
                    if(marketStock!=null){
                        // Perform buy Trade
                        if(stockAccount.buyTrade(marketStock, getAmount())){
                            JOptionPane.showMessageDialog(Stock, "Stocks purchased!");
                            flag=1;
                            dispose();
                        }else{
                            // If buy Trade fails,  due to insufficient balance
                            JOptionPane.showMessageDialog(Stock, "Not enough balance");
                            flag=1;
                        }

                    }

                    if(flag == 0)
                        JOptionPane.showMessageDialog(Stock, "Please enter a valid stock name");

                }

            }
        });

        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SecurityAccount stockAccount = getStockAccount(user);

                if(amount.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Stock, "Please enter the stock amount");
                }
                else if(stockName.getText().isEmpty()){
                    JOptionPane.showMessageDialog(Stock, "Please enter the stock name");
                }
                // Check if valid stock name entered
                else if(StockMarket.getStockByName(getStockName())!=null){
                    // fetch the stock from Stock Market
                    Stock sellStock = StockMarket.getStockByName(getStockName());

                    // Sell Trade is successful
                    if(stockAccount.sellTrade(sellStock,getAmount())>0){
                        JOptionPane.showMessageDialog(Stock, "Stocks Sold!");
                        dispose();
                    }else {
                        // If sell Trade fails
                        JOptionPane.showMessageDialog(Stock, "Could not sell stocks");
                    }

                }else{
                    // If invalid stock name entered
                    JOptionPane.showMessageDialog(Stock, "Please enter a valid stock name");
                }

            }
        });


    }

    private SecurityAccount getStockAccount(int userId) {
        /*
        Get list of Customer accounts through username
        new account, make it = accounts in list
        Returns the Security account from among the users accounts
         */
        SecurityAccount stockAccount = null;

        // Fetch the customer
        Customer customer = (Customer) FileHandler.getUserById(userId);

        for (Account account : customer.getAccounts()){
            if(account instanceof SecurityAccount){
                stockAccount = (SecurityAccount) account;
                return stockAccount;
            }
        }


        return stockAccount;
    }

    public static void main(String[] args){
        StockGUI frame=new StockGUI(1);
        frame.setTitle("Stock Portfolio");
        frame.setLocation(10,10);
        frame.setSize(1000,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
