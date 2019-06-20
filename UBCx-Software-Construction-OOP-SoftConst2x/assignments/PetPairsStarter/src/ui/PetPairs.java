/*
 *@author salimt
 */

package ui;

import model.Human;
import model.PetStore;
import model.pets.Bunny;
import model.pets.Cat;
import model.pets.Dog;
import model.pets.Parrot;
import model.pets.Pet;

import java.util.ArrayList;

public class PetPairs {

    private ArrayList<Pet> animals;
    private ArrayList<Human> people;

    public PetPairs() {
        this.animals = new ArrayList <>();
        this.people = new ArrayList <>();

        Cat cat = new Cat("calico", false, true, 55.00);
        Dog dog = new Dog("black");
        Parrot parrot = new Parrot("green and red", true, false, 20.00);
        Bunny bunny = new Bunny("white", false, false, 10.00);
        Human george = new Human("George");
        Human sally = new Human("Sally");
        Human jim = new Human("Jim");

        animals.add(cat);
        animals.add(dog);
        animals.add(parrot);
        animals.add(bunny);

        people.add(george);
        people.add(sally);
        people.add(jim);

        cat.adoptHuman(george);
        sally.addSpendibees(200.00);

        PetStore store = new PetStore();
        store.addPet(dog);
        store.addPet(parrot);
        store.addPet(cat);

        System.out.println();

        Pet foundPet = store.findMatchingPet("dog","black");

        if (foundPet != null && sally.canAffordPet(foundPet)) {
            System.out.println("Matching pet:\n" + foundPet + "\n");
            sally.adoptPet(foundPet);
        }

        int result = sally.numPetsOfSpecies("dog");
        System.out.println("Sally has " + result + " dog(s)");

        System.out.println();
        store.displayAllPetsWithAttributes(true, false, 50.00);
    }

}

