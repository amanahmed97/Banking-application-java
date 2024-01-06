import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Transfer extends JFrame {

    private JComboBox<String> withdrawAccount;
    private JComboBox<String> depositAccount;
    private JButton transferButton;
    private JButton cancelButton;
    private JTextField amount;
    private JPanel transferPanel;
    private JComboBox<CurrencyType> senderCurrencyType;
    private JComboBox<CurrencyType> receiverCurrencyType;

    private CurrencyType getCurrency(JComboBox<CurrencyType> currencyType){
        CurrencyType currency;
        if(currencyType.getSelectedItem() == CurrencyType.USD){
            currency = CurrencyType.USD;
        }else if(currencyType.getSelectedItem() == CurrencyType.CNY){
            currency = CurrencyType.CNY;
        }else if(currencyType.getSelectedItem() == CurrencyType.INR){
            currency = CurrencyType.INR;
        }else
            currency = CurrencyType.GBP;
        return currency;

    }

    private double getAmount(){
        return Double.parseDouble(amount.getText());
    }

    public Transfer(Customer user){

        setContentPane(transferPanel);
        setTitle("Transfer");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        ArrayList<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            withdrawAccount.addItem(account.getAccountId() + " " + account.getType());
            depositAccount.addItem(account.getAccountId() + " " + account.getType());
        }
        for (CurrencyType c : CurrencyType.values()) {
            senderCurrencyType.addItem(c);
            receiverCurrencyType.addItem(c);
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

        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(amount.getText().isEmpty()){
                    JOptionPane.showMessageDialog(transferPanel, "Please enter the deposit amount");
                }
                long outId = Long.parseLong(withdrawAccount.getSelectedItem().toString().split(" ")[0]);
                long inId = Long.parseLong(depositAccount.getSelectedItem().toString().split(" ")[0]);
                Account inAcc = null;
                Account outAcc= null;
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getAccountId() == inId)
                        inAcc = accounts.get(i);
                    if (accounts.get(i).getAccountId() == outId)
                        outAcc = accounts.get(i);
                }
                if(outAcc != null && inAcc != null) {
                    if(inAcc instanceof SecurityAccount){
                        if(getAmount()>=1000){
                            if (outAcc.withdraw(getCurrency(senderCurrencyType),getAmount())) {
                                inAcc.deposit(getCurrency(receiverCurrencyType), Exchange.exchangeCurrency(getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType), getAmount()));
                                FileHandler.updateAccount(outAcc);
                                FileHandler.updateAccount(inAcc);
                                new Transaction(outAcc, inAcc, getAmount(), getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType));
                                JOptionPane.showMessageDialog(transferPanel, "Amount transferred!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(transferPanel, "Not enough balance");
                            }
                        }else{
                            JOptionPane.showMessageDialog(transferPanel, "Amount must over 1000!");
                            dispose();
                        }
                    }else{
                        if (outAcc.withdraw(getCurrency(senderCurrencyType),getAmount())) {
                            inAcc.deposit(getCurrency(receiverCurrencyType), Exchange.exchangeCurrency(getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType), getAmount()));
                            FileHandler.updateAccount(outAcc);
                            FileHandler.updateAccount(inAcc);
                            new Transaction(outAcc, inAcc, getAmount(), getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType));
                            JOptionPane.showMessageDialog(transferPanel, "Amount transferred!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(transferPanel, "Not enough balance");
                        }
                    }
                }

            }
        });

    }

}
