import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ManagerAccountFrame extends JFrame implements ActionListener {
    private final Container container;
    private final String username;
    private final JLabel userLabel;
    private final JButton checkCustomerInfoButton;
    private final JButton viewTransactionButton;
    private final JButton profileButton;
    private final JButton interestButton;
    private final JButton stocksButton;
    private final JButton logoutButton;


    public ManagerAccountFrame(String username) {
        setTitle("Manager Account");
        setVisible(true);
        setBounds(10,10,1000,800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        container = getContentPane();
        userLabel = new JLabel();
        checkCustomerInfoButton = new JButton("Check customer accounts");
        viewTransactionButton = new JButton("View transactions");
        profileButton = new JButton("Profile");
        stocksButton = new JButton("Manage stocks");
        interestButton = new JButton("Set Interest Rate");
        logoutButton = new JButton("Logout");
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        this.username = username;
        userLabel.setText("Username: " + this.username);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(700, 100, 200, 50);
        profileButton.setBounds(300, 250, 400, 40);
        checkCustomerInfoButton.setBounds(300, 300, 400, 40);
        viewTransactionButton.setBounds(300, 350, 400, 40);
        stocksButton.setBounds(300, 400, 400, 40);
        interestButton.setBounds(300, 450, 400, 40);
        logoutButton.setBounds(700, 500, 100, 35);

    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(profileButton);
        container.add(checkCustomerInfoButton);
        container.add(viewTransactionButton);
        container.add(stocksButton);
        container.add(interestButton);
        container.add(logoutButton);
    }

    public void addActionEvent() {
        profileButton.addActionListener(this);
        checkCustomerInfoButton.addActionListener(this);
        viewTransactionButton.addActionListener(this);
        stocksButton.addActionListener(this);
        interestButton.addActionListener(this);
        logoutButton.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == profileButton) {
            new ProfileFrame(username);
        }
        if (e.getSource() == checkCustomerInfoButton) {
            new CustomerList();
        }
        if (e.getSource() == viewTransactionButton) {
//            TODO
//            get all the transactions info
            new ViewDailyTransactions(LocalDate.now());
        }
        if (e.getSource() == stocksButton) {
//            get the stock info from the csv
            new ManageStock();
        }
        if (e.getSource() == interestButton) {
            new InterestRateFrame();
        }
        if (e.getSource() == logoutButton) {
            dispose();
        }
    }

    //Preview
//    public static void main(String[] args){
//        ManagerAccountFrame frame=new ManagerAccountFrame("1");
//        frame.setTitle("Manager Account");
//        frame.setLocation(10,10);
//        frame.setSize(1000,800);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }


}