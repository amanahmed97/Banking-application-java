import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewCustomerAccountsFrame extends JFrame {
    private JPanel viewAccountsPanel;
    private JButton backButton;
    private JLabel userIDLabel;
    private JTextArea idField;
    private JTextArea accountField;
    private JTextArea balanceField;
    private JTextArea currencyField;


    public ViewCustomerAccountsFrame(int userId){
        setTitle("Customer Accounts");
        setContentPane(viewAccountsPanel);//cannot be null
        setResizable(true);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Customer customer = (Customer) FileHandler.getUserById(userId);
        userIDLabel.setText(String.valueOf(customer.getUserId()));
        ArrayList<Account> accounts = customer.getAccounts();
        System.out.println(accounts.size());
        for (int i = 0; i < accounts.size(); i++){
            String accountType = "";
            Account account = accounts.get(i);
            if(account.getType() == AccountType.SAVING)
                accountType = "Savings Account";
            else if(account.getType() == AccountType.CHECKING)
                accountType = "Checking Account";
            else if(account.getType() == AccountType.SECURITY)
                accountType = "Stock Account";
            else if(account.getType() == AccountType.LOAN)
                accountType = "Loan Account";
            System.out.println(account);
            idField.append(account.getAccountId() + "\n\n\n\n");
            accountField.append(accountType + "\n\n\n\n");
            if (account.getType() == AccountType.CHECKING)
                checkingBalance((CheckingAccount) account);
            else if (account.getType() == AccountType.SAVING)
                savingBalance((SavingAccount) account);
            else if (account.getType() == AccountType.LOAN)
                loanBalance((LoanAccount) account);
            else if (account.getType() == AccountType.SECURITY)
                stockBalance((SecurityAccount) account);
        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void checkingBalance(CheckingAccount account){
        for (CurrencyType c : CurrencyType.values()){
            double deposit = account.getDeposit(c);
            if (deposit != -1){
                balanceField.append(deposit+"\n");
                currencyField.append(c+"\n");
            } else {
                balanceField.append("N\\A\n");
                currencyField.append(c+"\n");
            }
        }
    }

    private void savingBalance(SavingAccount account){
        for (CurrencyType c : CurrencyType.values()){
            double deposit = account.getBalance(c);
            if (deposit != -1){
                balanceField.append(deposit+"\n");
                currencyField.append(c+"\n");
            } else {
                balanceField.append("N\\A\n");
                currencyField.append(c+"\n");
            }
        }
    }

    private void loanBalance(LoanAccount account){
        double deposit = account.getLoanAmount();
        if (account.getLoanDate() != null) {
            String type = account.getLoanType().toString();
            balanceField.append(deposit + " Paid by " + account.getPaidDate() + "\n\n\n\n");
            currencyField.append(type + "\n\n\n\n");
        }
    }

    private void stockBalance(SecurityAccount account){
        balanceField.append(account.getStockBalance() + "\n\n\n\n");
        currencyField.append(CurrencyType.USD + "\n\n\n\n");
    }

//Preview
//    public static void main(String[] args) {
//        ViewCustomerAccountsFrame frame=new ViewCustomerAccountsFrame(1);
//        frame.setTitle("Customer Accounts");
//        frame.setLocation(10,10);
//        frame.setSize(1000,800);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//    }
}
