package model;

public class ChattyIntegerSet extends HighVolumeIntegerSet {

    // NOTE: this project accompanies the Abstract IntegerSetTest video

    @Override
    public void insert(Integer num){
        System.out.println("Inserting "+num);
        // data.add(num); not what we want to do
        super.insert(num);
    }


}