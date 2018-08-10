package model;

import java.util.ArrayList;

public class HairSalon {

    private ArrayList<Customer> bookings;

    public HairSalon(){
        bookings = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            // note to students: this is a for-loop
            // it will repeatedly run the statements in this code block
            // incrementing i each time
            // until i is equal to 18
            bookings.add(i, null);
        }
    }

    // MODIFIES: this
    // EFFECTS:  books the customer into the requested timeslot
    public void makeNewBooking(Customer c, int bookingTime){
        System.out.println("Customer " + c.getName() + " has been booked at " + bookingTime);
        bookings.set(bookingTime, c);
    }

    //EFFECTS: prints out all the bookings.  If the time has not been booked, prints "available"
    public void printBookingsList(){
        for (int i = 9; i < 18; i++) { //change this to be the wrong end value
            Customer c = bookings.get(i);
            if (c != null){
                System.out.print(i + "hrs: ");
                c.printName(); //This causes a bug because you can't access methods on a null value!
            }
            else {
                System.out.print(i + "hrs: ");
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
            System.out.println("Yes the customer is booked at that time");
        }
        return true;
    }


}