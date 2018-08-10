package model;

public class Customer {

    private String name;

    public Customer(String name){
        System.out.println("Making a new Customer called "+name);
        this.name = name;
    }

    // getters
    public String getName() { return name; }

    // EFFECTS: prints out the name of this customer on the console
    public void printName(){
        System.out.println(" " + name + " ");
    }


}