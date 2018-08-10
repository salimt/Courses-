package model.cardio;

public class IndoorBike extends CardioMachine {

    // REQUIRES: minutes > 0, level >= 1
    public IndoorBike(int minutes, int level){
        super(minutes, level);
        hasIntervalSetting = false;
    }


}