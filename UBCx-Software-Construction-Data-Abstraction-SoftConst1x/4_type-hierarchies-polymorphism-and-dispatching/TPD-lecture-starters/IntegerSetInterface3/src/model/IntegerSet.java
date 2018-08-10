package model;

public interface IntegerSet {

    // NOTE: this project accompanies the Abstract IntegerSetTest video

    // MODIFIES: this
    // EFFECTS:  inserts integer into set unless it's already there, in which case do nothing
    void insert(Integer num);

    // MODIFIES: this
    // EFFECTS:  if the integer is in the integer set, then remove it from the integer set.
    //           Otherwise, do nothing.
    void remove(Integer num);

    // EFFECTS: if the integer is contained in the set, return true. Otherwise return false.
    boolean contains(Integer num);

    // EFFECTS: returns the size of the set
    int size();
}
