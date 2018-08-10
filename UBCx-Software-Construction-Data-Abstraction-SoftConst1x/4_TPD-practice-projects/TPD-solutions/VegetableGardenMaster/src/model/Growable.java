package model;

public interface Growable {

    // EFFECTS: harvests plant, if applicable
    void harvest();

    // EFFECTS: feeds the plant based on which nutrients are compatible
    void feed();

    // EFFECTS: gives the plant as much water as it should have
    void water();

    // EFFECTS: returns a string of special instructions for this plant
    String getInstructions();


}