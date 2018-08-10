package model;

public class ChattyIntegerSet extends HighVolumeIntegerSet {
    @Override
    public void insert(Integer num){
        System.out.println("Inserting "+num);
        // data.add(num); not what we want to do
        super.insert(num);
    }
}
