import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManageStock extends JFrame {

    private JTextField price;
    private JTextField stockName;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel ManageStock;
    private JTextArea stockList;

    private double getPrice(){
        return Double.parseDouble(price.getText());
    }
    private String getStockname() { return stockName.getText();}

    public ManageStock(){
        setContentPane(ManageStock);
        setTitle("Manage Stock");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Get list of stocks available in Stock Market
        // Add it to the list
        for (Stock stock : StockMarket.stockMarketList){
            stockList.append(stock.getStockName()+" :: Price: "+stock.getStockPrice()+"\n\n");
        }


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // check for valid input field values
                if(price.getText().isEmpty()){
                    JOptionPane.showMessageDialog(ManageStock, "Please enter the stock price");
                }
                else if(stockName.getText().isEmpty()){
                    JOptionPane.showMessageDialog(ManageStock, "Please enter the stock name");
                }else {
                    // Create new stock
                    Stock newStock = new Stock(StockMarket.stockMarketList.size()+1,getStockname(),getPrice(),99);

                    // add to stock market list
                    if(StockMarket.addStockToMarket(newStock)) {
                        JOptionPane.showMessageDialog(ManageStock, "stock added");
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(ManageStock, "stock not added");
                    }
                }
            }
        });
    }

}
