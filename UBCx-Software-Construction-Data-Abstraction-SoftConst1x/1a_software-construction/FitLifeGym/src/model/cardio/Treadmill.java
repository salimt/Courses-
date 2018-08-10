package model.cardio;

public class Treadmill extends CardioMachine {

    private double speed;

    // REQUIRES: minutes > 0, level >= 1, speed >= 1 in mph
    public Treadmill(int minutes, int level, double speed){
        super(minutes, level);
        this.speed = speed;
        hasIntervalSetting = true;
    }

    // getters
    public double getSpeed() { return speed; }

    // setters
    public void setSpeed(double speed) { this.speed = speed; }


}