package model.weights;

import java.util.ArrayList;
import java.util.List;

public class Barbell extends Weight {

    //REQUIRES: pounds >= 0
    public Barbell(int pounds){
        super(pounds);
    }


    //EFFECTS: returns a list of plates needed to add up to total weight
    public List<Plate> getPlates(){
        ArrayList<Plate> plates = new ArrayList<>();
        int remainingWeight = getWeight() - 45;

        while(remainingWeight > 0){
            if (remainingWeight >= 90){
                plates.add(new Plate(45));
                plates.add(new Plate(45));
                remainingWeight -= 90;
            } else if (remainingWeight >= 50) {
                plates.add(new Plate(25));
                plates.add(new Plate(25));
                remainingWeight -= 50;
            } else if (remainingWeight >= 20){
                plates.add(new Plate(10));
                plates.add(new Plate(10));
                remainingWeight -= 20;
            } else if (remainingWeight >= 10){
                plates.add(new Plate(5));
                plates.add(new Plate(5));
                remainingWeight -= 10;
            } else {
                for (int i = remainingWeight; i > 0; i--){
                    plates.add(new Plate(1));
                    remainingWeight--;
                }
            }
        }

        return plates;
    }
}
