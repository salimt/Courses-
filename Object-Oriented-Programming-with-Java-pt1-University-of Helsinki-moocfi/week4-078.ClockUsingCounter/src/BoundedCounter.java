/**
 * author: salimt
 */

public class BoundedCounter {
    private int value;
    private int upperLimit;

    public BoundedCounter(int upperLimit) {
        this.upperLimit = upperLimit;
        this.value = value;
        value = 0;
    }

    //increases the value until upperlimit-1
    public void next() {
        if(!(this.value >= this.upperLimit)){this.value++;}
        else{this.value = 0;}
    }

    //gets the value
    public int getValue() { return this.value; }

    //MODIFIES this
    public void setValue(int value) { if(!(value<0 || value>this.upperLimit)){this.value = value;} else{this.value = 0;} }

    public String toString() {
        if(value < 10){return "0" + this.value;}
        return "" + this.value;
    }
}