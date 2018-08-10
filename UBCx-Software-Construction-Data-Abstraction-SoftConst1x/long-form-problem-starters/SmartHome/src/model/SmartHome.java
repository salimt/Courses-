package model;

import model.appliances.Appliance;

import java.util.ArrayList;
import java.util.List;

public class SmartHome {

    private List<Appliance> appliances;
    private boolean home;

    public SmartHome(){
        appliances = new ArrayList<>();
        home = false;
    }

    //getters
    public boolean isHome() {
        return home;
    }

    public List<Appliance> getAllAppliances(){ return appliances; }


    //EFFECTS: returns a list of appliances in this currently running
    public List<Appliance> getAppliancesRunning(){
        List<Appliance> appliancesOn = new ArrayList<>();
        for(Appliance a : appliances){
            if(a.isRunning()){
                appliancesOn.add(a);
            }
        }
        return appliancesOn;
    }

    //REQUIRES: a != null
    //MODIFIES: this
    //EFFECTS: adds an appliance to SmartHome
    public void install(Appliance a){
        appliances.add(a);
    }

    //REQUIRES: a.minTemp() <= temp <= a.maxTemp(), a is installed in this
    //MODIFIES: a
    //EFFECTS: sets a to given temp
    public void setTemp(Appliance a, int temp){
        Appliance a1 = appliances.get(appliances.indexOf(a));
        a1.setTemp(temp);
    }

    //REQUIRES: a != null
    //MODIFIES: a
    //EFFECTS: sets a to 'on'
    public void turnOn(Appliance a){
        a.turnOn();
    }

    //REQUIRES: a != null
    //MODIFIES: a
    //EFFECTS: sets a to 'off'
    public void turnOff(Appliance a){
        a.turnOff();
    }

    //MODIFIES: this
    //EFFECTS: records that someone is home and prints out status report to console
    public void arriveHome(){
        home = true;
    }

    //MODIFIES: this
    //EFFECTS: records that everyone is away and prints out status report to console
    public void leaveHome(){
        home = false;
        for(Appliance a: appliances){
            if(a.runsWhileAway()){
                turnOn(a);
            } else {
                turnOff(a);
            }
        }
    }

    //EFFECTS: returns a String list of appliances running
    public String appsRunningStatus(){
        List<Appliance> running = getAppliancesRunning();
        StringBuilder status = new StringBuilder("");

        for(Appliance a: running){
            status.append("\n").append(a.status());
        }
        return status.toString();
    }

    //MODIFIES: this
    //EFFECTS: updates appliances running if not home
    public void update(){
        if(!isHome()){
            leaveHome();
        }
    }

}
