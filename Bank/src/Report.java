import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Report {
    public static HashMap<LocalDate, ArrayList<Transaction>> transactionList;

    public Report(){
        transactionList = new HashMap<>();
    }

    public static void addTransaction(LocalDate date, Transaction transaction){
        if (transactionList.containsKey(date))
            transactionList.get(date).add(transaction);
        else{
            ArrayList<Transaction> temp = new ArrayList<>();
            temp.add(transaction);
            transactionList.put(date, temp);
        }
        FileHandler.writeTransaction();
    }

    public static void addTransaction(Transaction transaction){
        addTransaction(LocalDate.now(), transaction);
    }
    // When run on terminal, change the file pathname by adding ../ at the front
    public boolean printReport(LocalDate date){
        if (transactionList.containsKey(date)){
            ArrayList<Transaction> temp = transactionList.get(date);
            File report = new File(date + ".txt");
            try {
                report.createNewFile();
                PrintWriter write = new PrintWriter(report);
                write.println("Daily Transaction Report on " + date);
                for (int i = 0; i < temp.size(); i++)
                    write.println(temp.get(i));
                write.close();
            }catch (IOException e){
                System.out.println("Error Occurred");
                System.out.println(e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }
}
