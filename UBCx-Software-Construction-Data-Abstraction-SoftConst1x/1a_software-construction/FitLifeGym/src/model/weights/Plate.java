package model.weights;

public class Plate extends Weight {

    public Plate(int pounds) {
        super(pounds);
    }

    // getters
    public int getTotalWeight() { return getWeight() * 2; }


}