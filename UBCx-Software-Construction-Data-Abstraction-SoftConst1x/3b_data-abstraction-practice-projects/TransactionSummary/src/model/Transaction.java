package model;

public class Transaction {

    private String name;
    private String date;
    private int amount;
    private TransactionType type;

    public Transaction(String name, String date, int amount, TransactionType type) {
       this.name = name;
       this.date = date;
       this.amount = amount;
       this.type = type;

    }

    // getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public int getAmount() { return amount; }
    public TransactionType getType() { return type; }


}
