
public class NumberStatistics {
    private int amountOfNumbers;
    private int sum;



    public NumberStatistics() {
        this.sum = 0;
        this.amountOfNumbers = amountOfNumbers;
    }

    //adds number
    public void addNumber(int number) {
        this.sum += number;
        this.amountOfNumbers++;
    }
    //return the amount of numbers added
    public int amountOfNumbers() { return this.amountOfNumbers; }
    //returns the sum of the given numbers
    public int sum() { return sum; }
    //return the average of the given numbers
    public double average() { if(!(this.sum == 0)){return (double)sum / (double)amountOfNumbers;} return 0.0; }

}