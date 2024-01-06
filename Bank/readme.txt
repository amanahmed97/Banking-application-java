# CS611-5
## Banking Application
---------------------------------------------------------------------------

Class Descriptions : 


Account.java - abstract class that will be extended by subclass accounts
AccountFactory.java - factory class that creates a new account through individual account factory
AccountFactoryInterface.java - interface that account factories must implement to create Account
AccountType.java - enum for types of account
BankManager.java - class that extends User superclass for representing Bank manager
CheckingAccount.java - subclass that extends Account superclass for checking account
CheckingFactory.java - factory class that helps to make a checking account
ChooseTransactions.form - form for choosing trasnactions
ChooseTransactions.java - class that extends JFrame that is used for choosing transaction
CloseAccount.form - form for closing account
CloseAccount.java - class that extends JFrame that is used for closing account
CreateAccountFrame.form - form for creating an account
CreateAccountFrame.java - class that extends JFrame that is used for creating an account
CurrencyType.java - enum for different currency types
CurrentLoansFrame.form - form for looking at loan accounts
CurrentLoansFrame.java - class that extends JFrame used for looking at current loans account
Customer.java - subclass that extends User superclass for representing Customer
CustomerFactory.java - simple customer Factory that is used for creatin a customer
CustomerList.form - form that is used to show list of customers
CustomerList.java - class that extends JFrame that is used to show all customers
CustomerMainMenuFrame.java - class that extends JFrame that is used to represent the main menu for customer
Deposit.form - form that is used for deposit
Deposit.java - class that extends JFrame that is used for deposit
Exchange.java - class that is used for exchanging currency
FileHandler.java - class that is used for read/write data into csv file
InterestRateFrame.form - form that is used for interest rate form
InterestRateFrame.java - class that extends JFrame that is used for interest rate setting
LoanAccount.java - subclass that extends Account superclass for representing loan account
LoanFactory.java - factory class that helps to make a loan account
LoanRequestFrame.form - form used to make a loan request
LoanRequestFrame.java - class that extends JFrame that is used to make loan request
LoginFrame.form - form for loggin in
LoginFrame.java - class that extends JFrame for logging in
ManagerAccountFrame.form - form for manager account
ManagerAccountFrame.java - class that extends JFrame used by the manager account
ManageStock.form - form that is used for managing stock
ManageStock.java - class that extends JFrame that is used for managing stock
Pay.form - form used for pay
Pay.java - class that extends JFrame that is used to pay
ProfileFrame.form - form that is used for profile
ProfileFrame.java - class that extends JFrame that is used for profile
RepayLoan.form - form that is used for repaying loan
RepayLoan.java - class that extends JFrame that is used to repay loan
Report.java - class that is used to handle report 
SavingAccount.java - subclass that extends Account superclass for representing a savings account
SavingDeposit.java - class that is used for saving account deposit
SavingFactory.java - factory class that helps to make a savings account
SecurityAccount.java - subclass that extends Account superclass for representing a security account
SecurityFactory.java - factory class that helps to make a security account
SignUpFrame.form - form for sign up
SignUpFrame.java - class that extends JFrame for signing up
Stock.java - class that handles stocks 
StockGUI.java - class that extends JFrame used for stock related activies
StockGUI.form - form for stock related action Performed
StockMarket.java - class that represents a stock market
Transaction.java - class that is used for each trasnactions
Transfer.form - form for transfer
Transfer.java - class that extends JFrame used for transfering from user to user 
User.java - superclass that represents all users like customer/BankManager
ViewCustomerAccountsFrame.form - form for showing customer accounts
ViewCustomerAccountsFrame.java - class that extends JFrame that shows customer accounts
ViewDailyTransactionsInfo.form - form for viewing daily trasnactions
ViewDailyTransactionsInfo.java - class that extends JFrame that is used for viewing daily transaction
WelcomeFrame.form - form for welcome screen
WelcomeFrame.java - class that extends JFrame that is used for welcome menu
Withdraw.form - form for withraw money
Withdraw.java - class that extends JFrame that is used for withrawing stuff

---------------------------------------------------------------------------

How To Compile

#After unzip the Bank.zip, navigate to src directory
（Will not get full functionality, recommend to run it in IntelliJ for full functions）
Run the following command line
1.javac -d bin WelcomeFrame.java
2.java -cp bin WelcomeFrame
#Run it in IntelliJ
