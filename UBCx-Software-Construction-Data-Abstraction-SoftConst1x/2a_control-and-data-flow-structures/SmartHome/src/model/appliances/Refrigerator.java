package model.appliances;

public class Refrigerator implements Appliance {

    private boolean running;
    private boolean runWhileAway;
    private int fridgeTemp;
    private int freezerTemp;

    public Refrigerator(int fridgeTemp){
        this.fridgeTemp = fridgeTemp;
        this.freezerTemp = -5;
        setRunsWhileAway(true);
    }

    //EFFECTS: returns fridge temperature
    public int getTemp() {
        return fridgeTemp;
    }

    //EFFECTS: returns freezer temperature
    public int getFreezerTemp() {
        return freezerTemp;
    }

    //MODIFIES: this
    //EFFECTS: sets temperature of the freezer
    public void setFreezerTemp(int freezerTemp) {
        this.freezerTemp = freezerTemp;
    }

    //MODIFIES: this
    //EFFECTS: sets temperature of the fridge
    public void setTemp(int fridgeTemp) {
        this.fridgeTemp = fridgeTemp;
    }

    //EFFECTS: returns the minimum temperature this appliance can be set to
    @Override
    public int minTemp() {
        return 0;
    }

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
        return "Refrigerator (Temp: " + getTemp() + ")";
    }


}
