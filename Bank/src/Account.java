public abstract class Account {
    private AccountType type;
    private long accountId;
    private int customerId;

    public Account(AccountType type, long accountId, int customerId){
        this.type = type;
        this.accountId = accountId;
        this.customerId = customerId;
    }

    public abstract boolean withdraw(CurrencyType currency, double amount);
    public abstract void deposit(CurrencyType currency, double amount);

    public AccountType getType() {
        return type;
    }

    public int getCustomerId() {
        return customerId;
    }

    public long getAccountId() {
        return accountId;
    }
}
