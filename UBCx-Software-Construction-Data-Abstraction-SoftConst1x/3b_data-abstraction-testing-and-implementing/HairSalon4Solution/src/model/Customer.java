package model;

public class Customer {

    private String name;
    private int bookedTime;

    public Customer(String name){
        System.out.println("Making a new Customer called "+name);
        this.name = name;
        this.bookedTime = 0;
    }

    // getters
    public String getName() { return name; }
    public int getBookingTime() { return bookedTime; }

    // setters
    public void setBookedTime(int time) { bookedTime = time; }

    // EFFECTS: prints out the name of this customer on the console
    public void printName() {
        System.out.println(" " + name + " ");
    }

    // EFFECTS: returns the bookedTime of this customer's appointment
    public int confirmBooking() {
        System.out.println(name+ ": Confirming that I am booked at "+bookedTime);
        return bookedTime;
    }


}