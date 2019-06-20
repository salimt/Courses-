package model.pets;

public class Dog extends Pet {

    private static final double DEFAULT_DOG_PRICE = 100.00;

    public Dog(String color, boolean friendly, boolean needsAttention, double price){
        super("dog", color, friendly, needsAttention, price);
    }

    public Dog(String color){
        super("dog", color, DEFAULT_DOG_PRICE);
    }

}
