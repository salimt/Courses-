package model.random;

import model.random.RandomNumber;

public class NumberSquare extends RandomNumber {

    private boolean stamped;

    public NumberSquare(){
        super();
    }

    //EFFECTS: returns true if stamped
    public boolean isStamped(){
        return stamped;
    }

    //MODIFIES: this
    //EFFECTS: stamps this number square
    public void stamp() {
        stamped = true;
    }

}
