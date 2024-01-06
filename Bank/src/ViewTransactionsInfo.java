import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTransactionsInfo extends JFrame {

    private JTextArea dateField;
    private JTextArea amountField;
    private JTextArea memoField;
    private JButton backButton;
    private JLabel dateLabel;
    private JLabel amountLabel;
    private JLabel memoLabel;
    private JPanel transactionPanel;
    private JLabel userIdLabel;
    private JTextArea userIdField;

    public ViewTransactionsInfo(Customer customer){
        setTitle("TransactionsInfo");
        setContentPane(transactionPanel);
        setResizable(true);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        transactionPanel.setAutoscrolls(true);
        HashMap<LocalDate, ArrayList<Transaction>> transactions = FileHandler.getTransactionByCustomer(customer.getAccounts());
        for (LocalDate d : transactions.keySet()){
            ArrayList<Transaction> tempT = transactions.get(d);
            for (Transaction t : tempT){
                userIdField.append(t.getReceiverAccount().getAccountId() + "\n\n");
                amountField.append(t.getSendAmount() + "\n\n");
                memoField.append("Send " + t.getSendType() + " Recv "+ t.getRecvType() + "\n\n");
                dateField.append(d + "\n\n");
            }
        }
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
