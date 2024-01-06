import java.util.*;

public class Customer extends User{
    private ArrayList<Account> accounts;
    private String firstName;
    private String lastName;

    private ArrayList<Transaction> transactions;

    public Customer(String name, String pwd, int id, String firstName, String lastName){
        super("C",name,pwd,id);
        accounts = new ArrayList<>();
        setFirstName(firstName);
        setLastName(lastName);
    }

    public void makeTransaction(){

    }

    public void addAccount(Account account){
        accounts.add(account);
    }

    public void removeAccount(long accoundId){
        for (int i = 0; i < accounts.size(); i++){
            if (accounts.get(i).getAccountId() == accoundId)
                accounts.remove(i);
        }
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
