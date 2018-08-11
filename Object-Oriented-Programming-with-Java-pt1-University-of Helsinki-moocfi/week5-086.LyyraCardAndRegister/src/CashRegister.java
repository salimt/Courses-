
public class CashRegister {

    private double cashInRegister;   // the amount of cash in the register
    private int economicalSold;      // the amount of economical lunches sold
    private int gourmetSold;         // the amount of gourmet lunches sold

    public CashRegister() {
        this.cashInRegister = 1000;  // at start the register has 1000 euros
    }

    public void loadMoneyToCard(LyyraCard card, double sum) {
        if(sum > 0){
            card.loadMoney(sum);
            this.cashInRegister += sum;
        }
    }

    public double payEconomical(double cashGiven) {
        double lunchPrice = 2.50;     // price of the economical lunch is 2.50 euros
        if(cashGiven >= lunchPrice){
            this.cashInRegister += lunchPrice;
            this.economicalSold += 1;
            return cashGiven - lunchPrice;
        }else{
            return cashGiven;
        }
    }

    public double payGourmet(double cashGiven) {
        double gourmetPrice = 4.00;     // price of the gourmet is 4.00 euros
        if(cashGiven >= gourmetPrice){
            this.cashInRegister += gourmetPrice;
            this.gourmetSold += 1;
            return cashGiven - gourmetPrice;
        }else{
            return cashGiven;
        }
    }

    public boolean payEconomical(LyyraCard card) {
        double lunchPrice = 2.50;     // price of the lunchPrice is 2.50 euros
        if(card.balance() >= lunchPrice){
            card.pay(lunchPrice);
            this.economicalSold += 1;
            return true;
        }else{
            return false;
        }
    }

    public boolean payGourmet(LyyraCard card) {
        double gourmetPrice = 4.00;     // price of the gourmet is 4.00 euros
        if(card.balance() >= gourmetPrice){
            card.pay(gourmetPrice);
            this.gourmetSold += 1;
            return true;
        }else{
            return false;
        }
    }

    public String toString() {
        return "money in register " + cashInRegister + " economical lunches sold: " + economicalSold + " gourmet lunches sold: " + gourmetSold;
    }
}
