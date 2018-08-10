package model.appliances;

public class Oven implements Appliance {

    private boolean running;
    private boolean runWhileAway;
    private boolean mode; //true if bake, false if broil
    private int temp;

    public Oven(int temp){
        this.temp = temp;
        mode = true;
    }

    //EFFECTS: returns true if oven is set to bake
    public boolean getMode() {
        return mode;
    }

    //EFFECTS: returns temperature of this appliance
    public int getTemp() {
        return temp;
    }

    //MODIFIES: this
    //EFFECTS: sets oven to bake setting
    public void bake() {
        mode = true;
    }

    //MODIFIES: this
    //EFFECTS: sets oven to broil setting
    public void broil(){
        mode = false;
    }

    //MODIFIES: this
    //EFFECTS: sets temperature to temp
    public void setTemp(int temp) {
        this.temp = temp;
    }

    //EFFECTS: returns the minimum temperature this appliance can be set to
    @Override
    public int minTemp() {
        return 0;
    }

    //EFFECTS: returns the maximum temperature this appliance can be set to
    @Override
    public int maxTemp() {
        return 450;
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
        return "Oven (Temp: " + getTemp() + ")";
    }
}
