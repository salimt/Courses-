package model.observer_pattern;

import model.random.BingoNumber;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {

    private List<Observer> observers;

    public Subject(){
        observers = new ArrayList<>();
    }

    public List<Observer> getObservers() {
        return observers;
    }

    //MODIFIES: this
    //EFFECTS: adds observer to list of observers
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //EFFECTS: notifies observers of state change
    public void notifyObservers(BingoNumber n){
        for(Observer o: observers){
            o.update(n);
        }
    }

}
