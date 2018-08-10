package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AmazonWarehouse {

    public static final int MAX_CAPACITY = 100;

    private String location;
    private List<Package> storage;
    private List<Package> shippingPackages;

    public AmazonWarehouse(String location) {
        this.location = location;
        storage = new ArrayList<>();
        shippingPackages = new ArrayList<>();
    }

    // getters
    public String getLocation() { return location; }
    public List<Package> getStorage() { return storage; }
    public List<Package> getShippingPackages() { return shippingPackages; }

    // MODIFIES: this
    // EFFECTS: adds the given package to storage, given that this warehouse's MAX_CAPACITY
    //          has not yet been met. Return true if successfully added, else false
    public boolean addPackage(Package p) {
        int currentCap = storage.size() + shippingPackages.size();
        if (currentCap < MAX_CAPACITY) {
            storage.add(p);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: storage is non-empty
    // MODIFIES: this
    // EFFECTS: adds the packages which have prime delivery to shippingPackages
    //          and removes it from storage
    public void preparePrimePackages() {
        Iterator<Package> storageIter = storage.iterator();
        while (storageIter.hasNext()) {
            Package p = storageIter.next();
            if (p.isPrime()) {
                addToShipping(p);
                storageIter.remove();
            }
        }
    }

    // REQUIRES: storage is non-empty
    // MODIFIES: this
    // EFFECTS: given a wgt, adds all packages in storage with weight < wgt to
    //          shippingPackages
    public void shipLightPackages(double wgt) {
        Iterator<Package> storageIter = storage.iterator();
        while (storageIter.hasNext()) {
            Package p = storageIter.next();
            if (p.getWeight() < wgt) {
                addToShipping(p);
                storageIter.remove();
            }
        }
    }

    // REQUIRES: storage is non-empty
    // EFFECTS: prints out all the packages in this warehouse in the format
    //          Name: _________ : Destination: _________
    public void printLabels() {
        for (Package p : storage) {
            System.out.println("Name: " + p.getOwner() + " Destination: " + p.getCity());
        }
        for (Package p : shippingPackages) {
            System.out.println("Name: " + p.getOwner() + " Destination: " + p.getCity());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds to shippingPackages if there is enough space
    private void addToShipping(Package p) {
        int currentCap = storage.size() + shippingPackages.size();
        if (currentCap < MAX_CAPACITY) {
            shippingPackages.add(p);
        }
    }


}