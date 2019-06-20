package model.weights;

public abstract class Weight {

    private int pounds;

    //REQUIRES: pounds >= 0
    public Weight(int pounds){
        this.pounds = pounds;
    }

    //EFFECTS: returns weight in lbs
    public int getWeight(){
        return pounds;
    }

    //REQUIRES: pounds >= 0
    //MODIFIES: this
    //EFFECTS: sets weight to pounds
    public void setWeight(int pounds){
        this.pounds = pounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Weight weight = (Weight) o;

        return pounds == weight.getWeight();
    }

    @Override
    public int hashCode() {
        return pounds * 31;
    }
}
