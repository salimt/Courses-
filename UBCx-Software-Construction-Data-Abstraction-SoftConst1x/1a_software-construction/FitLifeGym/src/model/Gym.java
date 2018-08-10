package model;

import model.cardio.CardioMachine;
import model.weights.Weight;

import java.util.ArrayList;
import java.util.List;

public class Gym {

    private List<Weight> weights;
    private List<CardioMachine> cardioMachines;
    private boolean hasPool;
    private boolean hasChildCare;


    public Gym() {
        weights = new ArrayList<>();
        cardioMachines = new ArrayList<>();
    }

    // getters
    public List<Weight> getWeights() { return weights; }
    public List<CardioMachine> getCardioMachines() { return cardioMachines; }
    public boolean hasChildCare() { return hasChildCare; }
    public boolean hasPool() { return hasPool; }
    public int getNumberOfWeights() { return weights.size(); }

    // setters
    public void setHasChildCare(boolean hasChildCare) { this.hasChildCare = hasChildCare; }
    public void setHasPool(boolean hasPool) { this.hasPool = hasPool; }
    public int getNumberOfCardioMachines() { return cardioMachines.size(); }


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

    // REQUIRES: the given weight must be in the gym already
    // MODIFIES: this
    // EFFECTS: removes this weight from the gym
    public void removeWeight(Weight weight){
        weights.remove(weight);
    }

    // REQUIRES: the given machine must already be in the gym
    // MODIFIES: this
    // EFFECTS: removes this machine from the gym
    public void removeCardioMachine(CardioMachine machine){
        cardioMachines.remove(machine);
    }


}