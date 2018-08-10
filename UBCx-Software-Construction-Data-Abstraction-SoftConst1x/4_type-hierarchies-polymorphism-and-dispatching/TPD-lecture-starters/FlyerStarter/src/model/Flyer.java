package model;

public interface Flyer {

    // NOTE: this project goes with the Subclasses & Extends, Pt. 2 (Extending) Video

    // REQUIRES: being on land
    // MODIFIES: this
    // EFFECTS:  moves this into the air
    public void takeOff();

    // REQUIRES: being in the air
    // MODIFIES: this
    // EFFECTS: moves this horizontally in the air
    public void fly();

    // REQUIRES: being in the air
    // MODIFIES: this
    // EFFECTS: moves this onto land
    public void land();


}