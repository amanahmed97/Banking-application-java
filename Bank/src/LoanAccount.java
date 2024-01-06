import java.time.LocalDate;

public class LoanAccount extends Account{
    private double loanAmount;
    private CurrencyType loanType;
    private double loanInterest;
    private LocalDate loanDate;
    private LocalDate paidDate;

    public LoanAccount(long accountId, int customerId) {
        super(AccountType.LOAN, accountId, customerId);
        this.loanAmount = 0;
        this.loanInterest = 0;
        this.loanDate = null;
        this.loanType = null;
        this.paidDate = null;
    }

    public LoanAccount(long accountId, int customerId, double loanAmount, double loanInterest, CurrencyType loanType, LocalDate loanDate, LocalDate paidDate){
        super(AccountType.LOAN, accountId, customerId);
        this.paidDate = paidDate;
        this.loanType = loanType;
        this.loanDate = loanDate;
        this.loanInterest = loanInterest;
        this.loanAmount = loanAmount;
    }
    //Must call when create a loan account
    public void requestLoan(double loanAmount, CurrencyType loanType, double loanInterest, long loanPeriod, LocalDate loanDate){
        this.loanAmount = loanAmount;
        this.loanType = loanType;
        this.loanInterest = loanInterest;
        this.loanDate = loanDate;
        this.paidDate = loanDate.plusDays(loanPeriod);
    }

    @Override // cannot withdraw from a loan
    public boolean withdraw(CurrencyType currency, double amount) {
        return false;
    }

    // if the loan is due after a month, then it will increase by interest rate
    public void increaseLoan(LocalDate date){
        if (date.equals(paidDate.plusMonths(1))) {
            loanAmount += loanAmount * loanInterest;
            paidDate = paidDate.plusMonths(1);
        }
    }

    @Override
    public void deposit(CurrencyType currency, double amount) {
        if (currency != loanType){
            double actualAmount = Exchange.exchangeCurrency(currency,loanType,amount);
            loanAmount -= actualAmount;
        }else{
            loanAmount -= amount;
        }
        if (loanAmount <= 0) {
            this.loanAmount = 0;
            this.loanInterest = 0;
            this.loanDate = null;
            this.loanType = null;
            this.paidDate = null;
        }
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public CurrencyType getLoanType() {
        return loanType;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public double getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(double interst) {
        this.loanInterest = interst;
    } 

    public LocalDate getPaidDate() {
        return paidDate;
    }
}
