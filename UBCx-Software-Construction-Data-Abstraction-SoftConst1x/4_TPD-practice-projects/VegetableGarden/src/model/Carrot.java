/**
 * author: salimt
 */

package model;

public class Carrot extends Vegetable {

    public Carrot() {
        super("Carrot", VegType.SEED);
    }

    public void feed(){ System.out.println("Carrot successfully fed!"); }
    public void water(){ System.out.println("Carrot successfully fed!"); }
    public String getInstructions(){ return "Don't eat it before wash it! Carrot"; }
    public void harvest(){ System.out.println("Carrot harvest time!"); }

}
