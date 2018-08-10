package model;

public class Package {

    private String owner;
    private String city;
    private PackageType type;
    private double weight;
    private boolean prime;

    public Package(String name, String destination, PackageType kind, double wgt, boolean priority) {
        owner = name;
        city = destination;
        type = kind;
        weight = wgt;
        prime = priority;
    }

    // getters
    public String getOwner() { return owner; }
    public String getCity() { return city; }
    public PackageType getType() { return type; }
    public double getWeight() { return weight; }
    public boolean isPrime() { return prime; }


}