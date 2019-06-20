/*
 *@author salimt
 */

package model;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import model.pets.Pet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PetStore {

    private HashMap<String, ArrayList<Pet>> animals;

    public PetStore() {
        this.animals = new HashMap<>();
    }

    //MODIFIES: this
    //EFFECTS: adds p to the petstore
    public void addPet(Pet p){
        ArrayList<Pet> pets = animals.get(p.getSpecies());
        if (pets==null) {
            pets = new ArrayList<>();
        }
        pets.add(p);
        animals.put(p.getSpecies(), pets);
    }

    //EFFECTS: finds a pet matching this species and color, if it exists in the store
    public Pet findMatchingPet(String species, String color){
        ArrayList<Pet> pets = animals.get(species);

        if (pets==null){
            return null;
        }

        Pet p = new Pet(species, color, 0.00);
        if (pets.contains(p)){
            int i = pets.indexOf(p);
            return pets.get(i);
        }
        return null;
    }

    //EFFECTS: prints out all pets in the store matching given attributes
    public void displayAllPetsWithAttributes(boolean friendly, boolean needsAttention, double price) {
        Collection <ArrayList<Pet>> allPets = animals.values();
        for(ArrayList petList: allPets){
            displayOneSpeciesWithAttributes(petList, friendly, needsAttention, price);
        }
    }


    //EFFECTS: prints out all pets of this species matching given attributes
    public void displayOneSpeciesWithAttributes(List <Pet> petList, boolean friendly, boolean needsAttention, double price) {
        for(Pet p: petList){
            if(p.getPrice() <= price && p.isFriendly() == friendly && p.needsAttention() == needsAttention){
                System.out.println("Has attributes: " + p);
            }
        }

    }

}
