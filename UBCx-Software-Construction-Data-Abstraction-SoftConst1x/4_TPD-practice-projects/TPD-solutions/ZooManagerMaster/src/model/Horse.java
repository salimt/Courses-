package model;

public class Horse extends Animal {

    private double topSpeed;

    public Horse(String name, String country, int age, Zookeeper careTaker, Double weight, double topSpeed) {
        super(name, country, age, careTaker, weight);
        this.topSpeed = topSpeed;
    }

    // getters
    public double getTopSpeed() { return topSpeed; }


}