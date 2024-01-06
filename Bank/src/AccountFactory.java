
//package Factory;



public class AccountFactory {

    public static Account createAccount (AccountType accountType, CurrencyType currencyType, int customerID) {


        AccountFactoryInterface f; 

        switch (accountType) {
            case CHECKING:
                f = new CheckingFactory(customerID, currencyType);
                break;
            case SAVING:
                f = new SavingFactory(customerID, currencyType);
                break;
            case LOAN:
                f = new LoanFactory(customerID, currencyType);
                break;
            case SECURITY:
                f = new SecurityFactory(customerID, currencyType);
                break;
            default:
                throw new UnknownError ("Wrong account type");
        }

        Account newAccount = f.createAccount();

        return newAccount;
    }
}