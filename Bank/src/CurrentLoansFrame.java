import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CurrentLoansFrame extends JFrame {
    private JTextArea loanId;
    private JTextArea collateral;
    private JTextArea loanAmt;
    private JTextArea roi;
    private JTextArea currency;
    private JButton repayLoanButton;
    private JButton backButton;
    private JLabel loanIdField;
    private JPanel currentLoansPanel;
    private JLabel collateralField;
    private JLabel roiField;
    private JLabel currencyField;
    private JLabel loanAmtField;
    private JLabel currentLoans;

    public CurrentLoansFrame(Customer customer) {
        setTitle("Loan Accounts");
        setContentPane(currentLoansPanel);
        setResizable(true);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        currentLoans.setFont(new Font("Serif", Font.BOLD, 20));
        ArrayList<Account> accounts = customer.getAccounts();
        ArrayList<LoanAccount> loanAccounts = new ArrayList<>();
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getType() == AccountType.LOAN){
                LoanAccount temp = (LoanAccount) accounts.get(i);
                loanId.append(temp.getAccountId() + "\n\n");
                collateral.append(temp.getPaidDate() + "\n\n");
                loanAmt.append(temp.getLoanAmount() + "\n\n");
                currency.append(temp.getLoanType() + "\n\n");
                roi.append(temp.getLoanInterest() + "\n\n");
                loanAccounts.add(temp);
            }
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        repayLoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loanAccounts.size() == 0){
                    JOptionPane.showMessageDialog(currentLoansPanel, "You don't have any loans");
                } else {
                    new RepayLoan(customer);
                    dispose();
                }
            }
        });
    }

}
