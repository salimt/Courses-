package model.Veggies;

import model.VegType;
import model.Vegetable;

public class Carrot extends Vegetable{

    public Carrot() {
        super("Carrot", VegType.ROOT);
        setInstructions("Plant 3-5 weeks before last spring frost");
    }

    @Override
    public void harvest() {
        System.out.println("Harvest at desired maturity. You can store carrots in the ground.");
    }

    @Override
    public void feed() {
        System.out.println("Fertilize 5-6 weeks after planting");
    }

    @Override
    public void water() {
        System.out.println("Water one inch per week");
    }


}