package model.appliances;

public interface Appliance {

    //EFFECTS: returns true if appliance is running
    boolean isRunning();

    //EFFECTS: returns true if appliance should run while away
    boolean runsWhileAway();

    //MODIFIES: this
    //EFFECTS: sets whether runs while away to b
    void setRunsWhileAway(boolean b);

    //MODIFIES: this
    //EFFECTS: turns appliance on
    void turnOn();

    //MODIFIES: this
    //EFFECTS: turns appliance off
    void turnOff();

    //EFFECTS: returns the appliance status in format "Name (Temp: )"
    String status();

    //MODIFIES: this
    //EFFECTS: sets temperature to temp
    void setTemp(int temp);

    //EFFECTS: returns temperature of this appliance
    int getTemp();

    //EFFECTS: returns the minimum temperature this appliance can be set to
    int minTemp();

    //EFFECTS: returns the maximum temperature this appliance can be set to
    int maxTemp();

}
