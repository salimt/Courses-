package model;

public class Snake extends Animal{

    private String name;
    private int age;
    private Zookeeper caretaker;
    private double weight;
    private double length;
    private boolean venom;

    public Snake(String nm, String ct, int age, Zookeeper zk, double wgt, double len, boolean vn) {
        super(nm, ct, age, zk, wgt);
        this.length = len;
        this.venom = vn;
    }

    // getters
    public double getLength() { return length; }
    public boolean isVenom() { return venom; }

}