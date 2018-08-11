/* 
 * Do not touch this!
 */

public class Account {

    private double balance;
    private String owner;

    public Account(String owner, double balance) {
        this.balance = balance;
        this.owner = owner;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdrawal(double amount) {
        balance -= amount;
    }

    public double balance() {
        return balance;
    }

    @Override
    public String toString() {
        return owner + " balance: " + balance;
    }
}
