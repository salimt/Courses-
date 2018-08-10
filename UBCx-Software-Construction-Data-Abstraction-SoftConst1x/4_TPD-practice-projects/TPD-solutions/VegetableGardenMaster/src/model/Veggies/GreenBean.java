package model.Veggies;

import model.VegType;
import model.Vegetable;

public class GreenBean extends Vegetable {

    public GreenBean() {
        super("Green Bean", VegType.SEED);
    }

    @Override
    public void harvest() {
        System.out.println("Sow every 2 weeks");
    }

    @Override
    public void feed() {
        // a Bean does not require additional nutrients
    }

    @Override
    public void water() {
        System.out.println("Water on sunny days");
    }


}