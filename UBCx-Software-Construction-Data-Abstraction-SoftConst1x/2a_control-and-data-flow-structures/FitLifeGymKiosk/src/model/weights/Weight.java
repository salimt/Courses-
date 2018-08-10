package model.weights;

public abstract class Weight {

    private int pounds;

    // REQUIRES: pounds >= 0
    public Weight(int pounds) {
        this.pounds = pounds;
    }

    // EFFECTS: returns weight in lbs
    public int getWeight() {
        return pounds;
    }

    // REQUIRES: pounds >= 0
    // MODIFIES: this
    // EFFECTS: sets weight to pounds
    public void setWeight(int pounds) {
        this.pounds = pounds;
    }

    // Don't worry about the methods below, you'll learn more about them in SoftConst2x
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Weight)) return false;
        if (o == this) return true;

        Weight wgt = (Weight) o;

        return hashCode() == wgt.hashCode();
    }

    @Override
    public int hashCode() {
        return pounds * 31;
    }


}