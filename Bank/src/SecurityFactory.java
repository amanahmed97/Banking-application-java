public class SecurityFactory implements AccountFactoryInterface {
    private int customerID;
    private CurrencyType currencyType;
    private int accountHolderId;
    private static int accountNumber = 0;


    public SecurityFactory (int customerID, CurrencyType currencyType) {
        this.currencyType = currencyType;
        this.customerID = customerID;
        accountNumber++;
        this.accountHolderId = accountNumber;
    }

    private long getAccountId(){
        long smallest = 1000000000000000L;
        long biggest = 9999999999999999L;
        return (long)(Math.random()*(biggest - smallest + 1)+smallest);
    }

    @Override
    public Account createAccount () {
        return new SecurityAccount(getAccountId(), customerID);
    }
}
