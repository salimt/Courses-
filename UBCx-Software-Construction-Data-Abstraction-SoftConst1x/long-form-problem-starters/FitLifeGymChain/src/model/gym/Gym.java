package model.gym;

import model.cardio.CardioMachine;
import model.weights.Weight;

import java.util.ArrayList;
import java.util.List;

public class Gym {

    private List<Weight> weights;
    private List<CardioMachine> cardioMachines;
    private List<FitnessClass> classes;
    private boolean hasPool;
    private boolean hasChildCare;
    private String locnName;

    public Gym(String locnName){
        this.locnName = locnName;
        weights = new ArrayList<>();
        cardioMachines = new ArrayList<>();
        classes = new ArrayList<>();
    }

    // getters
    public List<Weight> getWeights() { return weights; }
    public List<CardioMachine> getCardioMachines() { return cardioMachines; }
    public List<FitnessClass> getFitnessClasses() { return classes; }
    public boolean hasChildCare() { return hasChildCare; }
    public boolean hasPool() { return hasPool; }
    public String getLocationName() { return locnName; }

    // EFFECTS: returns number of cardio machines
    public int getNumberOfCardioMachines(){
        return cardioMachines.size();
    }

    // EFFECTS: returns number of weight machines
    public int getNumberOfWeights(){
        return weights.size();
    }

    // MODIFIES: this
    // EFFECTS: adds this weight to the gym
    public void addWeight(Weight weight) {
        weights.add(weight);
    }

    // MODIFIES: this
    // EFFECTS: adds this machine to the gym
    public void addCardioMachine(CardioMachine machine){
        cardioMachines.add(machine);
    }

    // MODIFIES: this
    // EFFECTS: removes this weight from the gym
    public void removeWeight(Weight weight){
        weights.remove(weight);
    }

    // MODIFIES: this
    // EFFECTS: removes this machine from the gym
    public void removeCardioMachine(CardioMachine machine){
        cardioMachines.remove(machine);
    }

    // MODIFIES: this
    // EFFECTS: sets has pool to hasPool
    public void setHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    // MODIFIES: this
    // EFFECTS: sets has child care to hasChildCare
    public void setHasChildCare(boolean hasChildCare) {
        this.hasChildCare = hasChildCare;
    }

    // REQUIRES: locnName != null
    // MODIFIES: this
    // EFFECTS: sets the location name to locnName
    public void setLocationName(String locnName){ this.locnName = locnName; }

    // REQUIRES: fitnessClass != null
    // MODIFIES: this
    // EFFECTS: adds this class the gym
    public void addFitnessClass(FitnessClass fitnessClass){
        classes.add(fitnessClass);
    }

    // REQUIRES: fitnessClass != null
    // MODIFIES: this
    // EFFECTS: removes the given class from classes at this gym
    public void removeFitnessClass(FitnessClass fitnessClass){
        classes.remove(fitnessClass);
    }


}