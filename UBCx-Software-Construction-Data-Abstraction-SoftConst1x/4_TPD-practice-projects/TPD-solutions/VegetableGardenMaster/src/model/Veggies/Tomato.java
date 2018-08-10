package model.Veggies;

import model.VegType;
import model.Vegetable;

public class Tomato extends Vegetable {

    public Tomato() {
        super("Tomato", VegType.SEED);
        setInstructions("Start seeds indoors 6-8 weeks early");
    }

    @Override
    public void harvest() {
        System.out.println("Pick when firm and very red");
    }

    @Override
    public void feed() {
        System.out.println("Fertilize 2 weeks before picking and 2 weeks after first picking");
    }

    @Override
    public void water() {
        System.out.println("Water generously for first few days, keep consistent");
    }


}