package model.cardio;

public class Elliptical extends CardioMachine {

    // REQUIRES: minutes > 0, level >= 1
    public Elliptical(int minutes, int level){
        super(minutes, level);
        hasIntervalSetting = true;
    }


}