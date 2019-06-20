/*
 *@author salimt
 */

package model;

import model.pets.Pet;

import java.util.ArrayList;
import java.util.Objects;


public class Human {
    private String name;
    private double spendibees;
    private ArrayList <Pet> pets;
    private Pet pet;

    public Human(String name) {
        this.name = name;
        this.spendibees = 0;
        this.pets = new ArrayList <>();
        this.pet = null;
    }

    //getters
    public double getSpendibees() {
        return spendibees;
    }

    public boolean hasPet(Pet pet) {
        return this.pets.contains(pet);
    }

    //REQUIRES: this can afford pet
    //MODIFIES: this, pet
    //EFFECTS: if this can afford pet, adopt pet and have pet adopt human
    public void adoptPet(Pet pet){
        System.out.println("Adopting a pet!");
        if (!this.equals(pet.getHuman())) {
            spendSpendibees(pet.getPrice());
            this.pets.add(pet);
            pet.setHuman(this);
            System.out.println("Success! Adopted " + pet);
        }
    }

    //REQUIRES: pet != null
    //EFFECTS: returns true if this has enough money to buy pet
    public boolean canAffordPet(Pet pet){
        return spendibees >= pet.getPrice();
    }

    //MODIFIES: this
    //EFFECTS: adds spendibees to this account
    public void addSpendibees(double spendibees) {
        this.spendibees += spendibees;
    }

    @Override
    public String toString() {
        return "Human: " + name;
    }


    //REQUIRES: spent >= spendibees
    //MODIFIES: this
    //EFFECTS: reduces spendibees by spent
    private void spendSpendibees(double spent) {
        this.spendibees -= spent;
    }

    //EFFECTS: returns the number of pets belonging to species
    public int numPetsOfSpecies(String species) {
        int num = 0;
        for(Pet p: pets){
            if(p.getSpecies().equals(species)){
                num++;
            }
        }
        return num;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        this.setPet(pet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;
        Human human = (Human) o;
        return Objects.equals(pet, human.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pet);
    }

    public Pet getPet() { return pet; }
}
