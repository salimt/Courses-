/**
 * author: salimt
 */

package ui;
import model.*;
import static model.TransactionType.*;

public class Main {
    public static void main(String[] args) {
        TransactionSummary ts = new TransactionSummary("Ada Lovelace");
        Transaction  t1 = new Transaction("Movie", "May 1st", 10, ENTERTAINMENT);
        Transaction  t3 = new Transaction("Movie", "May 1st", 10, ENTERTAINMENT);

        Transaction  t2 = new Transaction("Restaurant", "May 11th", 20, FOOD);

        ts.addTransaction(t1);
        ts.addTransaction(t2);
        ts.addTransaction(t3);

        System.out.println(ts.getTransactions());
        System.out.println(ts.getNumTransactions());
        System.out.println(ts.averageTransactionCost());
    }
}
