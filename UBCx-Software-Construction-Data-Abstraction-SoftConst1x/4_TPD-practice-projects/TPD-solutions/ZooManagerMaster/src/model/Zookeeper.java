package model;

import java.util.LinkedList;
import java.util.List;

public class Zookeeper {

    private String name;
    private int age;
    private Animal favourite;
    private List<Animal> animalList;


    public Zookeeper(String nm, int age) {
        this.name = nm;
        this.age = age;
        this.favourite = null;
        this.animalList = new LinkedList<>();
    }

    // getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public Animal getFavourite() { return favourite; }
    public List<Animal> getAnimalList() { return animalList; }

    // setters
    public void setFavourite(Animal a) { favourite = a; }

    // MODIFIES: this
    // EFFECTS: adds the given animal to the list
    public void addToList(Animal a) {
        if (!animalList.contains(a)) {
            animalList.add(a);
        }
    }

    // MODIFIES: this
    // EFFECTS: return true if the given animal is able to be removed from list, else false
    public boolean removeFromList(Animal a) {
        if (animalList.contains(a)) {
            animalList.remove(a);
            return true;
        } else {
            return false;
        }
    }


}