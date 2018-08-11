public class DecreasingCounter {
    private int value;  // instance variable that remembers the value of the counter
    private int initialVal;

    public DecreasingCounter(int valueAtStart) {
        this.value = valueAtStart;
        this.initialVal = value;
    }

    public void printValue() {
        System.out.println("value: " + this.value);
    }

    //decreases the value by one
    public void decrease() { if(this.value > 0){ value--; } }

    //sets the value of the counter to zero
    public void reset() { this.value = 0; }

    //returns the counter to its initial value
    public void setInitial() { this.value = initialVal; }


}
