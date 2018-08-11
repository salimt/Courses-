import java.util.ArrayList;
import java.util.Random;


public class LotteryNumbers {
    private ArrayList<Integer> numbers;

    public LotteryNumbers() {
        // Draw numbers as LotteryNumbers is created
        this.drawNumbers();

    }

    public ArrayList<Integer> numbers() {
        return this.numbers;
    }

    public void drawNumbers() {
        // We'll format a list for the numbers
        this.numbers = new ArrayList<Integer>();
        // Write the number drawing here using the method containsNumber()
        Random num = new Random();
        while(numbers.size() != 7){
            int drawNum = num.nextInt(39)+1;
            if(!containsNumber(drawNum)){ this.numbers.add(drawNum); }
        }
    }

    public boolean containsNumber(int number) {
        // Test here if the number is already in the drawn numbers
        for(Integer i: numbers){
            if(i.equals(number)){ return true; }
        }return false;
    }
}
