package model;

public interface Flyer {

    // NOTE: this project accompanies the Abstract Classes video

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