/**
 * author: salimt
 */

package model;

public interface Growable {

    // REQUIRES: being hungry
    // MODIFIES: this
    // EFFECTS:  feeds the given veg
    public void feed();

    // REQUIRES: being thirsty
    // MODIFIES: this
    // EFFECTS:  waters the veg
    public void water();


    // MODIFIES: this
    // EFFECTS:  gets the instructions for the veg
    public String getInstructions();

    // REQUIRES: big enough for harvest
    // MODIFIES: this
    // EFFECTS:  harvet
    public void harvest();

}
