package model;

import java.util.LinkedList;
import java.util.List;

public class TransactionSummary {

    private String name;
    private List<Transaction> transactions;

    public TransactionSummary(String name) {
        this.name = name;
        transactions = new LinkedList<>();
    }

    // getters
    public String getName() { return name; }
    public List<Transaction> getTransactions() { return transactions; }
    public int getNumTransactions() { return transactions.size(); }

    // REQUIRES: t is not already within transactions
    // MODIFIES: this
    // EFFECTS: adds the given transaction t to the list of transactions
    public void addTransaction(Transaction t) {
        transactions.add(t);
    }

    // EFFECTS: return true if t1.transactions contain all of the same
    // transactions as t2.transactions, else return false.
//    public boolean compareTransactions(TransactionSummary t1, TransactionSummary t2) {
//        return false;
//    }

    // REQUIRES: transactions is non-empty
    // EFFECTS: computes the average amount of money spent on a transaction
    public double averageTransactionCost() {
        int total = 0;
        for(Transaction i: transactions){total+= i.getAmount();}
        return (double)total/(double)getNumTransactions();
    }

    // REQUIRES: transactions is non-empty
    // EFFECTS:  returns the average cost of all transactions of type specificType in this
    //           TransactionSummary
    public double specificTypeAverage(TransactionType specificType) {
        double totalCost = 0;
        int numTransactions = 0;
        for (Transaction t : transactions) {
            if (t.getType().equals(specificType)) {
                totalCost += t.getAmount();
                numTransactions++;
            }
        }
        return totalCost / numTransactions;
    }

    // REQUIRES: transactions is non-empty
    // EFFECTS: returns the largest transaction (in terms of cost) in this TransactionSummary
    public Transaction largestTransaction() {
        Transaction largest = transactions.get(0);
        for (Transaction t: transactions) {
            if (t.getAmount() > largest.getAmount()) {
                largest = t;
            }
        }
        return largest;
    }

    // EFFECTS: returns true if the given transaction is contained within the list of transactions
    public boolean contains(Transaction t) {
        for(Transaction i: transactions){if(i.equals(t)){return true;}}
        return false;
    }


}
