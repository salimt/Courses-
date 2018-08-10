package model.Veggies;

import model.VegType;
import model.Vegetable;

public class Lettuce extends Vegetable {

    public Lettuce() {
        super("Lettuce", VegType.LEAF);
    }

    @Override
    public void harvest() {
        System.out.println("Harvest at full size, before maturity");
    }

    @Override
    public void feed() {
        System.out.println("Fertilize 3 weeks after transplanting");
    }

    @Override
    public void water() {
        System.out.println("Sprinkle with water when leaves are wilting");
    }


}