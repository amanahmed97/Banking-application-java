import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;

public class LoanRequestFrame extends JFrame {

    private JTextField loanAmount;
    private JLabel loanAmountField;
    private JLabel currencyField;
    private JLabel collateralType;
    private JLabel collateralAmountField;
    private JTextField collateralAmount;
    private JComboBox comboBoxCurrency;
    private JPanel loanRequestPanel;
    private JTextField collateral;
    private JButton requestLoanButton;
    private JButton backButton;
    private JLabel loanRequest;

    private LoanAccount checkForOpenLoan(ArrayList<Account> accounts){
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getType() == AccountType.LOAN){
                LoanAccount temp = (LoanAccount) accounts.get(i);
                if (temp.getLoanDate() == null)
                    return temp;
            }
        }
        return null;
    }

    public LoanRequestFrame(Customer user){
        setTitle("Loan Request Form");
        setContentPane(loanRequestPanel);
        setSize(1000, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loanRequest.setFont(new Font("Serif", Font.BOLD, 20));
        ArrayList<Account> accounts = user.getAccounts();
        for (CurrencyType c : CurrencyType.values())
            comboBoxCurrency.addItem(c);

        loanAmount.addKeyListener(new KeyAdapter() {
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

        collateralAmount.addKeyListener(new KeyAdapter() {
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

        loanAmount.addKeyListener(new KeyAdapter() {
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

        collateralAmount.addKeyListener(new KeyAdapter() {
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

        requestLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String loanAmt = loanAmount.getText();
                String collateralAmt = collateralAmount.getText();
                String collateralType = collateral.getText();
                if(loanAmt.isEmpty()){
                    JOptionPane.showMessageDialog(loanRequestPanel, "Please enter the loan Amount");
                }
                else if(collateralType.isEmpty()){
                    JOptionPane.showMessageDialog(loanRequestPanel, "Please enter the type of collateral");
                }
                else if(collateralAmt.isEmpty()){
                    JOptionPane.showMessageDialog(loanRequestPanel, "Please enter the collateral amount");
                }
                else if(Double.parseDouble(loanAmt) > Double.parseDouble(collateralAmt)){
                    JOptionPane.showMessageDialog(loanRequestPanel, "You cannot request loan greater than the collateral amount");
                }
                else if(checkForOpenLoan(accounts) == null){
                    JOptionPane.showMessageDialog(loanRequestPanel, "You cannot request loan with no loan accounts");
                    dispose();
                }else{
                    LoanAccount temp = checkForOpenLoan(accounts);
                    temp.requestLoan(Double.parseDouble(loanAmt), getCurrency(),0.1,100, LocalDate.now());
                    JOptionPane.showMessageDialog(loanRequestPanel, "Loan Approved!");
                    FileHandler.updateAccount(temp);
                    dispose();
                }
            }
        });
    }

    //TODO:get input currency
    private CurrencyType getCurrency(){
        CurrencyType currency = null;
        if(comboBoxCurrency.getSelectedItem() == CurrencyType.USD){
            currency = CurrencyType.USD;
        }else if(comboBoxCurrency.getSelectedItem() == CurrencyType.CNY){
            currency = CurrencyType.CNY;
        }else if(comboBoxCurrency.getSelectedItem() == CurrencyType.INR){
            currency = CurrencyType.INR;
        }else
            currency = CurrencyType.GBP;
        return currency;

    }

}
