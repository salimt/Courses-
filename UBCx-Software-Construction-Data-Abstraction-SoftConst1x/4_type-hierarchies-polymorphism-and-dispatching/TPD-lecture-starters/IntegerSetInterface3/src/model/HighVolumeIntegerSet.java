package model;

import java.util.Collection;
import java.util.HashSet;

public class HighVolumeIntegerSet implements IntegerSet {

    // NOTE: this project accompanies the Abstract IntegerSetTest video

    Collection<Integer> data;

    public HighVolumeIntegerSet(){
        data = new HashSet<Integer>();
    }

    // MODIFIES: this
    // EFFECTS:  inserts integer into set unless it's already there, in which case do nothing
    @Override
    public void insert(Integer num) {
        data.add(num);
    }

    // MODIFIES: this
    // EFFECTS:  if the integer is in the integer set, then remove it from the integer set.
    //           Otherwise, do nothing.
    @Override
    public void remove(Integer num) {
        data.remove(num);
    }

    // EFFECTS: if the integer is contained in the set, return true. Otherwise return false.
    @Override
    public boolean contains(Integer num) {
        return data.contains(num);
    }

    // EFFECTS: returns the size of the set
    @Override
    public int size() {
        return data.size();
    }


}