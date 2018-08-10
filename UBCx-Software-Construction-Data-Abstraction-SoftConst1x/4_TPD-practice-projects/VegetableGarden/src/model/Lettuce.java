/**
 * author: salimt
 */

package model;

public class Lettuce extends Vegetable {

    public Lettuce() {
        super("Lettuce", VegType.ROOT);
    }

    public void feed(){ System.out.println("Lettuce successfully fed!"); }
    public void water(){ System.out.println("Lettuce successfully fed!"); }
    public String getInstructions(){ return "Don't eat it before wash it! Lettuce"; }
    public void harvest(){ System.out.println("Lettuce harvest time!"); }

}
