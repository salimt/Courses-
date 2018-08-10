package model;

public class Elephant extends Animal {

    private String name;
    private String country;
    private int age;
    private Zookeeper careTaker;
    private double weight;

    public Elephant(String nm, String ct, int age, Zookeeper zk, double wgt) {
        super(nm, ct, age, zk, wgt);
    }

}