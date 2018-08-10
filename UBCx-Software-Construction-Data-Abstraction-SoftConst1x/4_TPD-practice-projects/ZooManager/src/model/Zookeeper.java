package model;
import java.util.*;

public class Zookeeper {

    private String name;
    private int age;
    private List<Animal> animalList;
    private Animal favourite;

    public Zookeeper(String name, int age) {
        this.name = name;
        this.age = age;
        animalList = new LinkedList<>();
        this.favourite = favourite;
    }

    //getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public List <Animal> getAnimalList() { return animalList; }
    public Animal getFavourite() { return favourite; }

    //sets the favorite animal
    public void setFavourite(Animal favourite) { this.favourite = favourite; }

    //adds the animal into the animalList
    public void addToList(Animal a){ animalList.add(a); }

    //returns true if successfully removed from animalList else false
    public boolean removeFromList(Animal a){
        for(Animal an: animalList){
            if(an.equals(a)){
                animalList.remove(a);
                return true;
            }
        }return false;
    }


}