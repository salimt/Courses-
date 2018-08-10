package model;

public class Customer {

    private String name;

    public Customer(String name){
        System.out.println("Making a new Customer called " + name);
    }

    // getters
    public String getName() { return name; }

    // EFFECTS: prints the name of this customer out on the console
    public void printName() {
        System.out.println(" " + name + " ");
    }


}