import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class RepayLoan extends JFrame {

    private JLabel repayLoan;
    private JComboBox comboBoxLoanAccount;
    private JLabel dueAmt;
    private JTextField payment;
    private JButton confirmButton;
    private JButton backButton;
    private JLabel accountField;
    private JLabel dueAmtLabel;
    private JLabel paymentLabel;
    private JPanel repayLoanPanel;

    public RepayLoan(Customer customer) {
        setTitle("Repay Loan");
        setContentPane(repayLoanPanel);
        setResizable(true);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        repayLoan.setFont(new Font("Serif", Font.BOLD, 20));

        payment.addKeyListener(new KeyAdapter() {
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
        ArrayList<Account> accounts = customer.getAccounts();
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getType() == AccountType.LOAN)
                comboBoxLoanAccount.addItem(accounts.get(i).getAccountId());
        }
        comboBoxLoanAccount.setSelectedIndex(0);
        Long accountId = Long.parseLong(comboBoxLoanAccount.getSelectedItem().toString());
        LoanAccount selectedAccount;
        dueAmt.setText(((LoanAccount)FileHandler.checkAccount(accountId,AccountType.LOAN)).getLoanAmount() + "");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        comboBoxLoanAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long accountId = Long.parseLong(comboBoxLoanAccount.getSelectedItem().toString());
                LoanAccount selectedAccount = (LoanAccount)FileHandler.checkAccount(accountId,AccountType.LOAN);
                System.out.println(accountId);
                dueAmt.setText(selectedAccount.getLoanAmount() + "");
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                TODO
//                here need to get list of user accounts through userID
                ArrayList<CheckingAccount> checkingAccounts = new ArrayList<>();
                Long accountId = Long.parseLong(comboBoxLoanAccount.getSelectedItem().toString());
                LoanAccount selectedAccount = null;
                for (int i = 0; i < accounts.size(); i++){
                    if (accounts.get(i).getType() == AccountType.CHECKING)
                        checkingAccounts.add((CheckingAccount) accounts.get(i));
                }
                for (int i = 0; i < customer.getAccounts().size();i++){
                    if (customer.getAccounts().get(i).getAccountId() == accountId && customer.getAccounts().get(i).getType() == AccountType.LOAN)
                        selectedAccount = (LoanAccount) customer.getAccounts().get(i);
                }
                String pay = payment.getText();
                if(pay.isEmpty()){
                    JOptionPane.showMessageDialog(repayLoanPanel, "Please enter payment amount");
                }else if (checkingAccounts.size() == 0) {
                    JOptionPane.showMessageDialog(repayLoanPanel, "Please create a checking account to make the payment");
                    dispose();
                }else{
                    CheckingAccount curAccount = null;
                    for (int i = 0; i < checkingAccounts.size(); i++) {
                        if (checkingAccounts.get(i).withdraw(selectedAccount.getLoanType(),0))
                            curAccount = checkingAccounts.get(i);
                    }
                    if(curAccount == null) {
                        JOptionPane.showMessageDialog(repayLoanPanel, "Please create a checking account to make the payment");
                        dispose();
                    }
//                    TODO
                    else if(Double.parseDouble(pay) > curAccount.getDeposit(selectedAccount.getLoanType())){
                        JOptionPane.showMessageDialog(repayLoanPanel, "You don't have enough balance");
                    }
                    else if(Double.parseDouble(pay) > selectedAccount.getLoanAmount()){
                        JOptionPane.showMessageDialog(repayLoanPanel, "Please enter an amount less than the due amount");
                    }
                    else {
                        double payLoan = Double.parseDouble(pay);
                        CurrencyType loanType = selectedAccount.getLoanType();
                        curAccount.withdraw(selectedAccount.getLoanType(),payLoan);
                        selectedAccount.deposit(selectedAccount.getLoanType(),payLoan);
                        FileHandler.updateAccount(curAccount);
                        FileHandler.updateAccount(selectedAccount);
                        new Transaction(curAccount,selectedAccount,payLoan,loanType,loanType);
                        JOptionPane.showMessageDialog(repayLoanPanel, "Payment successful");
                        dispose();
                    }

                }
            }
        });

    }
}
