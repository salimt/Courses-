
public class Money {

    private final int euros;
    private final int cents;

    public Money(int euros, int cents) {

        if (cents > 99) {
            euros += cents / 100;
            cents %= 100;
        }

        this.euros = euros;
        this.cents = cents;
    }

    public int euros() {
        return euros;
    }

    public int cents() {
        return cents;
    }

    public Money plus(Money added){

        int newEuros = this.euros + added.euros;
        int newCents = this.cents + added.cents;
        Money newMoney = new Money(newEuros, newCents);
        return newMoney;

    }

    public boolean less(Money compared){

        if((this.euros + (float)this.cents()/100) < (compared.euros + (float)compared.cents()/100)){
            return true;
        }return false;

    }

    public Money minus(Money decremented){

        int newEuros = this.euros - decremented.euros;
        int newCents = this.cents - decremented.cents;

        if((this.euros + (float)this.cents()/100) - (decremented.euros + (float)decremented.cents()/100) < 0){
            return new Money(0, 0);
        }else if(this.cents - decremented.cents < 0){
            newCents += 100;
            newEuros--;
        }

        return new Money(newEuros, newCents);

    }

    @Override
    public String toString() {
        String zero = "";
        if (cents < 10) {
            zero = "0";
        }

        return euros + "." + zero + cents + "e";
    }

}
