package model.appliances;

public class Fireplace implements Appliance {

    private boolean running;
    private boolean runWhileAway;
    private int temp;

    public Fireplace(int temp){
        this.temp = temp;
    }

    //EFFECTS: returns temperature of this appliance
    public int getTemp() {
        return temp;
    }

    //MODIFIES: this
    //EFFECTS: sets temperature to temp
    public void setTemp(int temp) {
        this.temp = temp;
    }

    //heat setting between 0-10
    //EFFECTS: returns the minimum temperature this appliance can be set to
    @Override
    public int minTemp() {
        return 0;
    }

    //heat setting between 0-10
    //EFFECTS: returns the maximum temperature this appliance can be set to
    @Override
    public int maxTemp() {
        return 10;
    }

    //EFFECTS: returns true if appliance is running
    @Override
    public boolean isRunning() {
        return running;
    }

    //EFFECTS: returns true if appliance should run while away
    @Override
    public boolean runsWhileAway() {
        return runWhileAway;
    }

    //MODIFIES: this
    //EFFECTS: sets whether runs while away to b
    @Override
    public void setRunsWhileAway(boolean b) {
        runWhileAway = b;
    }

    //MODIFIES: this
    //EFFECTS: turns appliance on
    @Override
    public void turnOn() {
        running = true;
    }

    //MODIFIES: this
    //EFFECTS: turns appliance off
    @Override
    public void turnOff() {
        running = false;
    }

    //EFFECTS: returns the appliance status in format "Name (Temp: )"
    @Override
    public String status() {
        return "Fireplace (Temp: " + getTemp() + ")";
    }
}
