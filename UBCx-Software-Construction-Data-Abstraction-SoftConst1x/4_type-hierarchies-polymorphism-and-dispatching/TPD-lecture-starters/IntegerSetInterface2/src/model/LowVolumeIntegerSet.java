package model;

import java.util.ArrayList;

public class LowVolumeIntegerSet implements IntegerSet {

    // NOTE: this project accompanies the Subclasses & Extends, Part 4 (Super calls) video

    ArrayList<Integer> internalArray;

    public LowVolumeIntegerSet(){
        internalArray = new ArrayList<Integer>();
    }

    // MODIFIES: this
    // EFFECTS: inserts integer into set unless it's already there, in which case do nothing
    @Override
    public void insert(Integer num){
        if(!internalArray.contains(num)){
            internalArray.add(num);
        }
    }


    // MODIFIES: this
    // EFFECTS:  if the integer is in the integer set, then remove it from the integer set.
    //          Otherwise, do nothing.
    @Override
    public void remove(Integer num){
    }

    // EFFECTS: if the integer is contained in the set, return true. Otherwise return false.
    @Override
    public boolean contains(Integer num){
        return internalArray.contains(num);
    }

    // EFFECTS: returns the size of the set
    @Override
    public int size(){
        return internalArray.size();
    }


}