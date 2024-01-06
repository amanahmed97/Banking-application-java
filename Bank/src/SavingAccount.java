import java.time.LocalDate;
import java.util.HashMap;

public class SavingAccount extends Account{
    private boolean stockEligibility = false;
    private double interestSaving;
    private CurrencyType stockType = null;
    private HashMap<CurrencyType, SavingDeposit> balance;

    //when created, must deposit something in it.
    public SavingAccount(long accountHolderId, int customerId) {
        super(AccountType.SAVING, accountHolderId, customerId);
        balance = new HashMap<>();
        this.interestSaving = 0;
    }

    public SavingAccount(long accountHolderId, int customerId, boolean stockEligibility, double interestSaving, CurrencyType stockType, HashMap<CurrencyType, SavingDeposit> balance){
        super(AccountType.SAVING, accountHolderId, customerId);
        this.stockEligibility = stockEligibility;
        this.stockType = stockType;
        this.interestSaving = interestSaving;
        this.balance = new HashMap<>();
        for (CurrencyType c : balance.keySet()){
            this.balance.put(c,balance.get(c));
        }
    }

    public CurrencyType getStockType() {
        return stockType;
    }

    public HashMap<CurrencyType, SavingDeposit> getBalance() {
        return balance;
    }

    public double getInterestSaving() {
        return interestSaving;
    }

    public void setInterestSaving(double interestSaving) {
        this.interestSaving = interestSaving;
    }


    @Override
    public boolean withdraw(CurrencyType currency, double amount) {
        if (!balance.containsKey(currency))
            return false;
        else
        if (balance.get(currency).getAmount() < amount)
            return false;
        else {
            double tempD = balance.get(currency).getAmount();
            balance.get(currency).setWithdrawTimes(balance.get(currency).getWithdrawTimes()-1);
            balance.get(currency).setAmount(tempD - amount);
            if (currency == CurrencyType.USD)
                if (balance.get(currency).getAmount() < 2500){
                    stockEligibility = false;
                    stockType = null;
                }
            else
                if (currency == stockType && (Exchange.exchangeCurrency(currency,CurrencyType.USD,balance.get(currency).getAmount())) < 2500) {
                    stockEligibility = false;
                    stockType = null;
                }
            return true;
        }
    }

    public void deposit(CurrencyType currency, SavingDeposit deposit){
        balance.put(currency,deposit);
    }

    @Override
    public void deposit(CurrencyType currency, double amount) {
        if (balance.containsKey(currency))
            balance.get(currency).setAmount(balance.get(currency).getAmount() + amount);
        else{
            SavingDeposit tempS = new SavingDeposit(LocalDate.now(), amount);
            balance.put(currency,tempS);
        }
        if (currency == CurrencyType.USD)
            if (balance.get(currency).getAmount() >= 5000){
                stockEligibility = true;
                stockType = currency;
            }
        else
            if (Exchange.exchangeCurrency(currency,CurrencyType.USD,balance.get(currency).getAmount()) >= 5000) {
                stockEligibility = true;
                stockType = currency;
            }
    }

    public void interestIncrease(LocalDate date){
        for(CurrencyType i : balance.keySet()){
            SavingDeposit temp = balance.get(i);
            if (temp.getDepositDate().plusMonths(1).equals(date))
                temp.setAmount(temp.getAmount() + temp.getAmount() * interestSaving);
                temp.setDepositDate(temp.getDepositDate().plusMonths(1));
        }
    }

    public LocalDate getDepositDate(CurrencyType currency){
        if (balance.containsKey(currency))
            return balance.get(currency).getDepositDate();
        else
            return null;
    }

    public double getBalance(CurrencyType currency){
        if (balance.containsKey(currency))
            return balance.get(currency).getAmount();
        else
            return -1;
    }

    public boolean checkSecurityTransferEligibility(){
        return stockEligibility;
    }

}
