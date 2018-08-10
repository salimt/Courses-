package model.weights;

public class ResistanceBand {

    private int level;

    // REQUIRES: 1 <= level <= 5
    public ResistanceBand(int level){
        this.level = level;
    }

    // getters
    public int getLevel() { return level; }

    // EFFECTS: returns the color of this band, depending on the level
    public String getColor(){
        switch(level){
            case 1: return "Green";
            case 2: return "Blue";
            case 3: return "Purple";
            case 4: return "Yellow";
            default: return "Red";
        }
    }


}