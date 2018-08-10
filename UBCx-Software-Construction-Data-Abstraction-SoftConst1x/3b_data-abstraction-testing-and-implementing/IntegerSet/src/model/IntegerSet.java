package model;

import java.util.ArrayList;

public class IntegerSet {
    
    ArrayList<Integer> internalArray;

    public IntegerSet() {
       internalArray = new ArrayList<Integer>();
    }

    // MODIFIES: this
    // EFFECTS: inserts integer into set unless it's already there, in which case do nothing
    public void insert(Integer num) {
        if(!internalArray.contains(num)) {
            internalArray.add(num);
        }
    }

    // MODIFIES: this
    // EFFECTS: if the integer is in the integer set, them remove it from the integer set.
    //          Otherwise, do nothing
    public void remove(Integer num) {
        if (internalArray.contains(num)) {
            internalArray.remove(num);
        }

    }

    // EFFECTS: if the integer is contained within the set, return true. Otherwise return false
    public boolean contains(Integer num) {
        return internalArray.contains(num);
    }

    // EFFECTS: returns the size of the set
    public int size() {
        return internalArray.size();
    }


}