import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ViewDailyTransactions extends JFrame {


    private JPanel dailyTransactionPanel;
    private JLabel senderIdLabel;
    private JLabel receiverIdLabel;
    private JTextArea memoField;
    private JLabel amountLabel;
    private JButton backButton;
    private JTextArea senderIdField;
    private JTextArea receiverIdField;
    private JTextArea amountField;
    private JLabel memoLabel;

//    TODO
    public ViewDailyTransactions(LocalDate date){
        setTitle("DailyTransactionsInfo");
        setContentPane(dailyTransactionPanel);
        setResizable(true);
        setVisible(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dailyTransactionPanel.setAutoscrolls(true);
        ArrayList<Transaction> transactions = Report.transactionList.get(date);
        for (Transaction t : transactions){
            senderIdField.append(t.getSenderAccount().getAccountId() + "\n\n");
            receiverIdField.append(t.getReceiverAccount().getAccountId() + "\n\n");
            memoField.append("Send " + t.getSendType() + " Receive " + t.getRecvType() + "\n\n");
            amountField.append(t.getSendAmount() + "\n\n");
        }

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}
