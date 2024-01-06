import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class CreateAccountFrame extends JFrame {
    private JPanel createAccountPanel;
    private JComboBox<AccountType> comboBoxAccountType;//here is Drop-down list box
    private JComboBox<CurrencyType> comboBoxCurrencyType;//here is Drop-down list box
    private JButton createAccountButton;
    private JTextField InitialDepositTextField;
    private JLabel createAccount;
    private JButton backButton;

    private Account checkAccount(ArrayList<Account> accounts, AccountType accountType){
        for (int i = 0; i < accounts.size(); i++)
            if (accounts.get(i).getType() == accountType)
                return accounts.get(i);
        return null;
    }

    private SavingAccount checkStockEligibleAccount(ArrayList<Account> accounts) {
        for (int i = 0; i < accounts.size(); i++)
            if (accounts.get(i).getType() == AccountType.SAVING){
                SavingAccount temp = (SavingAccount) accounts.get(i);
                if (temp.checkSecurityTransferEligibility())
                    return temp;
            }

        return null;
    }

    public CreateAccountFrame(Customer user){
        setTitle("Create Account Form");
        setContentPane(createAccountPanel);
        setTitle("Create Account Form");
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAccount.setFont(new Font("Serif", Font.BOLD, 20));
        comboBoxAccountType.addItem(AccountType.CHECKING);
        comboBoxAccountType.addItem(AccountType.SAVING);
        comboBoxAccountType.addItem(AccountType.SECURITY);
        comboBoxAccountType.addItem(AccountType.LOAN);

        comboBoxCurrencyType.addItem(CurrencyType.CNY);
        comboBoxCurrencyType.addItem(CurrencyType.INR);
        comboBoxCurrencyType.addItem(CurrencyType.USD);
        comboBoxCurrencyType.addItem(CurrencyType.GBP);

        //only input should be number
        InitialDepositTextField.addKeyListener(new KeyAdapter() {
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Account> accounts = user.getAccounts();
                System.out.println("test"+comboBoxCurrencyType.getSelectedItem());

                if(InitialDepositTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(createAccountPanel, "Please add an initial deposit amount");
                }
                else if(getDeposit() < 100.0){
                    JOptionPane.showMessageDialog(createAccountPanel, "Initial Deposit cannot less than 100");
                }
                else if(comboBoxAccountType.getSelectedItem() == AccountType.SECURITY){
                    if (checkAccount(accounts,AccountType.SAVING) == null){
                        JOptionPane.showMessageDialog(createAccountPanel, "You don't have a saving account. You must have more than $5000 in your saving account");
                    } else {
                        SavingAccount temp = checkStockEligibleAccount(accounts);
                        if (temp == null){
                            JOptionPane.showMessageDialog(createAccountPanel, "You must have more than $5000 in your saving account");
                        } else {
                            SecurityAccount tempS = (SecurityAccount) AccountFactory.createAccount(AccountType.SECURITY,CurrencyType.USD,user.getUserId());
                            CheckingAccount tempC = (CheckingAccount) checkAccount(accounts,AccountType.CHECKING);
                            if (getDeposit() < 1000){
                                JOptionPane.showMessageDialog(createAccountPanel, "Initial Deposit for saving cannot less than 1000");
                            } else if (tempC == null) {
                                JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                            } else if (tempC.withdraw(CurrencyType.USD,1)) {
                                tempS.deposit((CurrencyType) comboBoxCurrencyType.getSelectedItem(),getDeposit());
                                FileHandler.addAccount(tempS);
                                user.addAccount(tempS);
                                JOptionPane.showMessageDialog(createAccountPanel, "Stock Account Created!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                            }
                        }
                    }
                } else if (comboBoxAccountType.getSelectedItem() == AccountType.LOAN){
                    LoanAccount tempL = (LoanAccount) AccountFactory.createAccount(AccountType.LOAN,getCurrency(),user.getUserId());
                    CheckingAccount tempC = (CheckingAccount) checkAccount(accounts,AccountType.CHECKING);
                    if (tempC == null) {
                        JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                    } else if (tempC.withdraw(CurrencyType.USD,1)) {
                        FileHandler.addAccount(tempL);
                        user.addAccount(tempL);
                        JOptionPane.showMessageDialog(createAccountPanel, "Loan Account Created!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                    }
                } else if (comboBoxAccountType.getSelectedItem() == AccountType.CHECKING){
                    CheckingAccount tempC = (CheckingAccount) AccountFactory.createAccount(AccountType.CHECKING,getCurrency(),user.getUserId());
                    tempC.deposit(getCurrency(),getDeposit());
                    CheckingAccount temp = (CheckingAccount) checkAccount(accounts,AccountType.CHECKING);
                    if (temp == null)// if there is no checking account at all
                        if (!tempC.withdraw(CurrencyType.USD,1)) {
                            JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                        } else {
                            FileHandler.addAccount(tempC);
                            user.addAccount(tempC);
                            JOptionPane.showMessageDialog(createAccountPanel, "Checking Account Created!");
                            dispose();
                        }
                    else { // if there is one checking account
                        if (temp.withdraw(CurrencyType.USD, 1)) {
                            FileHandler.addAccount(tempC);
                            user.addAccount(tempC);
                            JOptionPane.showMessageDialog(createAccountPanel, "Checking Account Created!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                        }
                    }
                } else {
                    SavingAccount tempS = (SavingAccount) AccountFactory.createAccount(AccountType.SAVING,getCurrency(),user.getUserId());
                    CheckingAccount tempC = (CheckingAccount) checkAccount(accounts,AccountType.CHECKING);
                    if (tempC == null) {
                        JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                    } else if (tempC.withdraw(CurrencyType.USD,1)) {
                        tempS.deposit(getCurrency(),getDeposit());
                        FileHandler.addAccount(tempS);
                        user.addAccount(tempS);
                        JOptionPane.showMessageDialog(createAccountPanel, "Saving Account Created!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(createAccountPanel, "Not able to pay account creation fee");
                    }
                }
            }
        });
    }

    //get start deposit
    private double getDeposit(){
        return Double.parseDouble(InitialDepositTextField.getText());
    }

    private CurrencyType getCurrency(){
        CurrencyType currency = null;
        if(comboBoxCurrencyType.getSelectedItem() == CurrencyType.USD){
            currency = CurrencyType.USD;
        }else if(comboBoxCurrencyType.getSelectedItem() == CurrencyType.CNY){
            currency = CurrencyType.CNY;
        }else if(comboBoxCurrencyType.getSelectedItem() == CurrencyType.INR){
            currency = CurrencyType.INR;
        }else
            currency = CurrencyType.GBP;
        return currency;

    }

//    public static void main(String[] args) {
//        Customer user = new Customer("miaki", "l", 1,"Longdan","Mao");
//        CreateAccountFrame c = new CreateAccountFrame(user);
//    }
}
