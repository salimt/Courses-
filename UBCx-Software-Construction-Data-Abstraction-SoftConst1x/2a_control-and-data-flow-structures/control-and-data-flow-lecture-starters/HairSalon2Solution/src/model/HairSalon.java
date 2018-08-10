package model;

import java.util.ArrayList;

public class HairSalon {
    private ArrayList<Customer> bookings;

    //Effects: creates a hair salon with available booking times from 0-17hrs.
    public HairSalon(){
        System.out.println("Creating a hair salon with 17 times");
        bookings = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            // note to students: this is a for-loop
            // it will repeatedly run the statements in this code block
            // incrementing i each time
            // until i is equal to 18
            bookings.add(i, null);
        }
    }

    //MODIFIES: this and Customer
    //EFFECTS:  books the customer into the requested timeslot, and let's the Customer know the booking time.
    public void makeNewBooking(Customer c, int bookingTime){
        System.out.println("Customer "+c.getName()+" has been booked at "+bookingTime);
        bookings.set(bookingTime,c);
        c.setBookedTime(bookingTime);
    }


    //EFFECTS: prints out all the bookings.  If the time has not been booked, prints "available"
    public void printBookingsList(){
        for (int i = 9; i < 18; i++) { //change this to be the wrong end value
            Customer c = bookings.get(i);
            if (c!=null){
                System.out.print(i+"hrs: ");
                c.printName(); //This causes a bug because you can't access methods on a null value!
            }
            else {
                System.out.print(i+"hrs: ");
                System.out.println(" available ");
            }
        }
    }

    //EFFECTS: returns true if the customer is found at the booking time.
    public boolean verifyBooking(Customer c, int bookingTime){
        Customer bookedCustomer = bookings.get(bookingTime);
        if (bookedCustomer == null){
            System.out.println("There is no customer booked at that time");
            return false;
        }
        if (bookedCustomer.getName() == c.getName()){
            System.out.println("Verifying: "+c.getName()+" is booked at "+bookingTime);
        }
        return true;
    }

    //REQUIRES: a booked time as input
    //EFFECTS: returns true if the customer is booked at the booking time
    public boolean confirmBookedName(String cName, int bookingTime){
        Customer bookedCustomer = bookings.get(bookingTime);
        Customer namedPerson = new Customer(cName);
        String customerName = bookedCustomer.getName();
        boolean isPersonBooked = customerName.equals(cName);
//        boolean isPersonBooked = bookedCustomer==namedPerson;
        return isPersonBooked;
    }


    //MODIFIES: this and Customer
    //EFFECTS:  changes the customer booked in the bookings, and let's the Customer know the new booking time.
    public void changeBooking(Customer customer, int newTime){
        int bookedTime = customer.getBookingTime();
        System.out.print(customer.getName()+"'s time is changing from "+bookedTime);
        // bookedTime = newTime;
        customer.setBookedTime(newTime);
        System.out.println(" to "+ newTime);
        bookings.set(newTime, customer);
        bookings.set(bookedTime, null);
    }

}
