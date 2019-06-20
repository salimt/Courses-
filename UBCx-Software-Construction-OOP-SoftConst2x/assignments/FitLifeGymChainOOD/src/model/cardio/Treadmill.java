package model.cardio;

public class Treadmill extends CardioMachine {

    private double speed;

    //REQUIRES: minutes > 0, level >= 1, speed >= 1 in mph
    public Treadmill(){
        hasIntervalSetting = true;
    }

    //EFFECTS: returns speed in mph
    public double getSpeed() {
        return speed;
    }

    //REQUIRES: speed >= 1 in mph
    //MODIFIES: this
    //EFFECTS: sets this speed to given speed
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
