package model;

public class Snake extends Animal {

    private double length;
    private boolean venom;

    public Snake(String name, String country, int age, Zookeeper careTaker, Double weight, double length, boolean venom) {
        super(name, country, age, careTaker, weight);
        this.length = length;
        this.venom = venom;
    }

   // getters
    public double getLength() { return length; }
    public boolean isVenom() { return venom; }


}