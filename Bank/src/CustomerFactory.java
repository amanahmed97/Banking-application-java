public class CustomerFactory {
    private static int customerId = 0;
    public static Customer createCustomer(String name, String pwd, String firstName, String lastName){
        customerId++;
        return new Customer(name, pwd, customerId, firstName, lastName);
    }
}
