package model.Veggies;

import model.VegType;
import model.Vegetable;

public class Radish extends Vegetable {

    public Radish() {
        super("Radish", VegType.ROOT);
        setInstructions("Plant 4-6 weeks before last frost, in ground");
    }

    @Override
    public void harvest() {
        System.out.println("Harvest three weeks after planting");
    }

    @Override
    public void feed() {
        // a radish does not need additional nutrients
    }

    @Override
    public void water() {
        System.out.println("Keep soil evenly moist");
    }


}