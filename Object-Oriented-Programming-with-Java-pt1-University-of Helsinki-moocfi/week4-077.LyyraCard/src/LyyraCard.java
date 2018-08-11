/**
 * author: salimt
 */

public class LyyraCard {

    private double balance;

    public LyyraCard(double balanceAtStart) { this.balance = balanceAtStart; }

    //decrease the balance by 2.50 euros
    public void payEconomical() { if(balance >= 2.50){balance -= 2.50;} }

    //decrease the balance by 4.00 euros
    public void payGourmet() { if(balance >= 4.00){balance -= 4.00;} }

    //if balance > 150, it'll be truncated to 150 euros.
    //should increase the balance of the card by the given amount
    public void loadMoney(double amount) { if(!(amount < 0)){balance += amount;} if(balance>150){balance = 150;} }


    public String toString() {
        return "The card has " + this.balance + " euros";
    }
}
