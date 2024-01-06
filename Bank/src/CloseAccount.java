import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class CloseAccount extends JFrame {
    private JComboBox accounts;
    private JButton closeAccountButton;
    private JPanel closeAccountPanel;
    private JLabel closeAccount;
    private JLabel selectField;
    private JButton backButton;

    private Account checkAccount(ArrayList<Account> accounts, AccountType accountType){
        for (int i = 0; i < accounts.size(); i++)
            if (accounts.get(i).getType() == accountType)
                return accounts.get(i);
        return null;
    }

    public CloseAccount(Customer user){
        setTitle("Close Account");
        setContentPane(closeAccountPanel);
        setSize(1000, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ArrayList<Account> accountList = user.getAccounts();
        for (int i = 0; i < accountList.size(); i++)
            accounts.addItem(accountList.get(i).getAccountId() + " " + accountList.get(i).getType());

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        closeAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long accountId = Long.parseLong(accounts.getSelectedItem().toString().split(" ")[0]);
                for (int i = 0; i < accountList.size(); i++){
                    if (accountList.get(i).getAccountId() == accountId){
                        CheckingAccount temp = (CheckingAccount) checkAccount(accountList,AccountType.CHECKING);
                        if (temp == null){
                            JOptionPane.showMessageDialog(closeAccountPanel, "Not able to pay account closing fee");
                        } else if (temp.withdraw(CurrencyType.USD,1)) {
                            FileHandler.removeAccount(accountList.get(i));
                            user.removeAccount(accountList.get(i).getAccountId());
                            JOptionPane.showMessageDialog(closeAccountPanel, "Account closed");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(closeAccountPanel, "Not able to pay account closing fee");
                        }

                    }
                }
            }
        });


    }
}
