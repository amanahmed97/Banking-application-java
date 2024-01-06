import java.time.LocalDate;

public class SavingDeposit {
    private LocalDate depositDate;
    private double amount;
    private int withdrawTimes = 6;


    public SavingDeposit(LocalDate depositDate, double amount){
        this.depositDate = depositDate;
        this.amount = amount;
    }

    public int getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(int withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public LocalDate getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate = depositDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
