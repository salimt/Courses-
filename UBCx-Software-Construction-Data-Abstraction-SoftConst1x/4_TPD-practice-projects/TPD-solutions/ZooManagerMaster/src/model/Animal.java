package model;

public class Animal {

    protected String name;
    protected String country;
    protected int age;
    protected Zookeeper careTaker;
    protected Double weight;


    public Animal(String name, String country, int age, Zookeeper careTaker, Double weight) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.careTaker = careTaker;
        this.weight = weight;
    }

    // getters
    public String getName() { return name; }
    public String getCountry() { return country; }
    public int getAge() { return age; }
    public Zookeeper getCareTaker() { return careTaker; }
    public Double getWeight() { return weight; }


}