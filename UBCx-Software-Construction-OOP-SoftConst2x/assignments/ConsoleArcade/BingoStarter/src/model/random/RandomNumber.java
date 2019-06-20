package model.random;

import java.util.Random;

public class RandomNumber {

    private Integer number;
    private static final int MAX_VALUE = 5;

    //EFFECTS: randomly generates a number between 0 and MAX_VALUE
    public RandomNumber() {
        Random randomGenerator = new Random();
        this.number = randomGenerator.nextInt(MAX_VALUE);
    }

    //EFFECTS: returns number
    public Integer getNumber(){
        return number;
    }
}
