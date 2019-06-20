package model.pets;

public class Bunny extends Pet {

    private static final double DEFAULT_BUNNY_PRICE = 15.00;

    public Bunny(String color, boolean friendly, boolean needsAttention, double price){
        super("bunny", color, friendly, needsAttention, price);
    }

    public Bunny(String color){
        super("bunny", color, DEFAULT_BUNNY_PRICE);
    }

}
