package model;

import model.cardio.CardioMachine;
import model.members.Member;
import model.weights.Weight;

import java.util.ArrayList;
import java.util.List;

public class Gym {

    private List<Weight> weights;
    private List<CardioMachine> cardioMachines;
    private List<FitnessClass> classes;
    private List<Member> members;
    private boolean hasPool;
    private boolean hasChildCare;

    public Gym(){
        weights = new ArrayList<>();
        cardioMachines = new ArrayList<>();
        classes = new ArrayList<>();
        members = new ArrayList<>();
    }

    // getters
    public List<Weight> getWeights() { return weights; }
    public List<CardioMachine> getCardioMachines() { return cardioMachines; }
    public List<Member> getMembers() { return members; }
    public List<FitnessClass> getFitnessClasses() { return classes; }
    public boolean hasChildCare() { return hasChildCare; }
    public boolean hasPool() { return hasPool; }

    // EFFECTS: returns number of cardio machines
    public int getNumberOfCardioMachines(){
        return cardioMachines.size();
    }

    // EFFECTS: returns number of weight machines
    public int getNumberOfWeights(){
        return weights.size();
    }

    // REQUIRES: weight != null
    // MODIFIES: this
    // EFFECTS: adds this weight to the gym
    public void addWeight(Weight weight) {
        weights.add(weight);
    }

    // REQUIRES: machine != null
    // MODIFIES: this
    // EFFECTS: adds this machine to the gym
    public void addCardioMachine(CardioMachine machine){
        cardioMachines.add(machine);
    }

    // REQUIRES: weight != null
    // MODIFIES: this
    // EFFECTS: removes this weight from the gym
    public void removeWeight(Weight weight){
        weights.remove(weight);
    }

    // REQUIRES: machine != null
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

    // REQUIRES: m != null
    // MODIFIES: this
    // EFFECTS: adds member m to this gym
    public void signUpMember(Member m){
        members.add(m);
    }

    // REQUIRES: m != null
    // MODIFIES: this
    // EFFECTS: removes member m from this gym
    public void removeMember(Member m) {
        members.remove(m);
    }


}