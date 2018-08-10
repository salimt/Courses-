/**
 * author: salimt
 */

package model;

public class Animal {
    protected String name;
    protected int age;
    protected Zookeeper caretaker;
    protected double weight;
    protected String country;

    public Animal(String nm, String ct, int age, Zookeeper zk, double wgt) {
        name = nm;
        this.age = age;
        caretaker = zk;
        weight = wgt;
        country = ct;
    }

    //getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public Zookeeper getCaretaker() { return caretaker; }
    public double getWeight() { return weight; }
    public String getCountry() { return country; }
}
