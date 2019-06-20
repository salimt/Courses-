package model.weights;

public class Plate extends Weight {

    //REQUIRES: pounds >= 0
    public Plate(int pounds) {
        super(pounds);
    }


    //EFFECTS: returns the total weight for two matching plates of this weight
    public int getTotalWeight(){
        return getWeight() * 2;
    }

}
