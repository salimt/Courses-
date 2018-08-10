package model.weights;

public abstract class Weight {

    private int pounds;

    public Weight(int pounds){
        this.pounds = pounds;
    }

    // getters
    public int getWeight() { return pounds; }

    // setters
    public void setWeight(int pounds) { this.pounds = pounds; }

    // Don't worry too much about the methods below, equals(Object o) checks for equality between this
    // and the object passed as parameter.
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