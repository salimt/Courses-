package model;

public class Whale extends Animal {

    private boolean waterType;
    private double maxDiveDepth;

    public Whale(String name, String country, int age, Zookeeper careTaker, Double weight, boolean waterType, double maxDiveDepth) {
        super(name, country, age, careTaker, weight);
        this.waterType = waterType;
        this.maxDiveDepth = maxDiveDepth;
    }

    // getters
    public double getMaxDiveDepth() { return maxDiveDepth; }
    public boolean isWaterType() { return waterType; }


}