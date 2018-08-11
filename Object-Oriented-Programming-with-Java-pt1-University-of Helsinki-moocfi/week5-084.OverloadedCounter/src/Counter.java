/**
 * author: salimt
 */

public class Counter {
    private int value;
    private boolean check;

    //Constructor
    public Counter() {
        this(0, false);
    }

    //sets the value and check
    public Counter(int startingValue, boolean check){
        this.value = startingValue;
        this.check = check;
    }

    //sets the value
    public Counter(int startingValue) {
        this(startingValue, false);
    }

    //sets the check
    public Counter(boolean check){
        this.value = 0;
        if(check==true){this.check = true; }
    }

    //return the value
    public int value(){ return this.value; }

    //increases the value by +1
    public void increase(){ this.value += 1; }
    public void increase(int increaseAmount) { if(increaseAmount >= 0){ this.value += increaseAmount; } }

    //decreases the value by -1
    public void decrease(){
        if(this.check == true && (this.value - 1 <= 0)){ this.value = 0; }
        else{ this.value -= 1; };
    }

    //decreases value by the given amount
    public void decrease(int decreaseAmount){
        if(this.check == true && (this.value - decreaseAmount <= 0)){this.value = 0;}
        else if(decreaseAmount < 0){}
        else{this.value -= decreaseAmount; }
    }

    public String toString(){
        return this.value + " is now " + this.check;
    }




}
