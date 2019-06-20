package model.pets;

public class Cat extends Pet {

    private static final double DEFAULT_CAT_PRICE = 50.00;

    public Cat(String color, boolean friendly, boolean needsAttention, double price){
        super("cat", color, friendly, needsAttention, price);
    }

    public Cat(String color){
        super("cat", color, DEFAULT_CAT_PRICE);
    }

}
