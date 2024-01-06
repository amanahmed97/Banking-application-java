import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pay extends JFrame {

    private JButton cancelButton;
    private JTextField receiver;
    private JTextField amount;
    private JComboBox<String> accountsList;
    private JButton payButton;
    private JPanel PayPanel;
    private JComboBox receiverCurrencyType;
    private JComboBox senderCurrencyType;

    private long getReceiver(){
        return Long.parseLong(receiver.getText());
    }
    private double getAmount(){
        return Double.parseDouble(amount.getText());
    }

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

    public Pay(Customer user){
        setContentPane(PayPanel);
        setTitle("View Accounts Form");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        ArrayList<Account> accounts = user.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getType() == AccountType.CHECKING || accounts.get(i).getType() == AccountType.SAVING)
                accountsList.addItem(accounts.get(i).getAccountId() + " " + accounts.get(i).getType());
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
        receiver.addKeyListener(new KeyAdapter() {
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

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (receiver.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(PayPanel, "Please enter the receiver ID");
                    } else if (amount.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(PayPanel, "Please enter the deposit amount");
                    }
                    else{
                        long receiverId = getReceiver();
                        Account recvAccount = null;
                        for (AccountType a : AccountType.values()){
                            recvAccount = FileHandler.checkAccount(receiverId,a);
                            if (recvAccount != null)
                                break;
                        }
                        if (recvAccount == null){
                            JOptionPane.showMessageDialog(PayPanel, "Receiver Account Not Found. Please enter the receiver ID");
                        }else {
                            long sendId = Long.parseLong(accountsList.getSelectedItem().toString().split(" ")[0]);
                            AccountType sendType = AccountType.valueOf(accountsList.getSelectedItem().toString().split(" ")[1]);
                            Account sendAccount = FileHandler.checkAccount(sendId, sendType);
                            if (sendAccount.withdraw(getCurrency(senderCurrencyType), getAmount())) {
                                recvAccount.deposit(getCurrency(receiverCurrencyType), Exchange.exchangeCurrency(getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType), getAmount()));
                                FileHandler.updateAccount(sendAccount);
                                FileHandler.updateAccount(recvAccount);
                                new Transaction(sendAccount, recvAccount, getAmount(), getCurrency(senderCurrencyType), getCurrency(receiverCurrencyType));
                                JOptionPane.showMessageDialog(PayPanel, "Amount transferred!");
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(PayPanel, "Not enough balance");
                            }
                        }
                        }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(PayPanel, "Please enter the receiver ID");
                }
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
