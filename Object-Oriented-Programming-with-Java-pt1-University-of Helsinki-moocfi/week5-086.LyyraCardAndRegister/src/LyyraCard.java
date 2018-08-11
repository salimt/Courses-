
public class LyyraCard {

    private double balance;

    public LyyraCard(double balance) {
        this.balance = balance;
    }

    public double balance() {
        return this.balance;
    }

    public void loadMoney(double amount) {
        this.balance += amount;
    }

    // method checks if the balance of the card is at least amount given as parameter if so return true; else false
    public boolean pay(double amount) {

        if(balance >= amount){
            this.balance -= amount;
            return true;
        }
        return false;


    }
}
