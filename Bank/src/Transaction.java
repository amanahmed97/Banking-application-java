import java.time.LocalDate;

public class Transaction {
    private Account senderAccount;
    private Account receiverAccount;
    private double sendAmount;
    private CurrencyType sendType;
    private CurrencyType recvType;

    public Transaction(Account senderAccount, Account receiverAccount, double sendAmount, CurrencyType sendType, CurrencyType recvType, LocalDate date){
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.sendAmount = sendAmount;
        this.sendType = sendType;
        this.recvType = recvType;
        Report.addTransaction(date, this);
    }

    public Transaction(Account senderAccount, Account receiverAccount, double sendAmount, CurrencyType sendType, CurrencyType recvType){
         this.senderAccount = senderAccount;
         this.receiverAccount = receiverAccount;
         this.sendAmount = sendAmount;
         this.sendType = sendType;
         this.recvType = recvType;
         Report.addTransaction(this);
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public double getSendAmount() {
        return sendAmount;
    }

    public CurrencyType getRecvType() {
        return recvType;
    }

    public CurrencyType getSendType() {
        return sendType;
    }

    @Override //need to specify account toString
    public String toString(){
        return senderAccount + " sends " + sendAmount + sendType + " to " + receiverAccount;
    }
}
