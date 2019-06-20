package model.weights;

public class Dumbbell extends Weight {

    public Dumbbell(int pounds){
        super(pounds);
    }

    //EFFECTS: returns the total weight for two matching dumbbells of this weight
    public int getTotalWeight(){
        return getWeight() * 2;
    }

}
