/**
 * author: salimt
 */

public class isOdd {
    private int evenNums;
    private int oddNums;

    public isOdd() {

    }
    //adds number
    public void addNumber(int number) {
        if(number % 2 == 0){ this.evenNums += number; }
        else{ this.oddNums += number; }
    }

    //getters
    public int getOdds() { return this.oddNums; }
    public int getEvens() { return this.evenNums; }
}
