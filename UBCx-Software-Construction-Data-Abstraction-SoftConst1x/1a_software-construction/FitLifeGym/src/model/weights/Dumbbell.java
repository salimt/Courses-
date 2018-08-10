package model.weights;

public class Dumbbell extends Weight {

    public Dumbbell(int pounds){
        super(pounds);
    }

    // getters
    public int getTotalWeight() { return getWeight() * 2; }


}
