/**
 * author: salimt
 */

package model;

public class Tomato extends Vegetable {

    public Tomato() {
        super("Tomato", VegType.LEAF);
    }

    public void feed(){ System.out.println("Tomato successfully fed!"); }
    public void water(){ System.out.println("Tomato successfully fed!"); }
    public String getInstructions(){ return "Don't eat it before wash it! Tomato"; }
    public void harvest(){ System.out.println("Tomato harvest time!"); }

}
