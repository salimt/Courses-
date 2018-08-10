package model;

import java.util.ArrayList;

public class HairSalon {

    private ArrayList<Customer> bookings;

    public HairSalon(){
        bookings = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            // note to students: this is a for-loop
            // it will repeatedly run the statements in this code block
            // incrementing i each time
            // until i is equal to 18
            bookings.add(i, null);
        }
    }

    // MODIFIES: this and Customer
    // EFFECTS:  books the customer into the requested timeslot if it is a valid timeslot, and let's the Customer know the booking time.
    public void makeNewBooking(Customer c, int bookingTime) {
        System.out.println("Customer "+ c.getName() + " has been booked at " + bookingTime++);
        bookings.set(bookingTime, c);
        c.setBookedTime(bookingTime);
    }

    // EFFECTS: prints out all the bookings.  If the time has not been booked, prints "available"
    public void printBookingsList() {
        for (int i = 9; i <= 19; i++) {
            Customer c = bookings.get(i);
            if (c != null){
                System.out.print(i + "hrs: ");
                c.printName();
            }
            else {
                System.out.print(i + "hrs: ");
                System.out.println(" available ");
            }
        }
    }

    // EFFECTS: returns true if the customer is found at the booking time.
    public boolean verifyBooking(Customer c, int bookingTime) {
        Customer bookedCustomer = bookings.get(bookingTime);
        if (bookedCustomer != null){
            System.out.println("There is no customer booked at that time");
            return false;
        }
        if (bookedCustomer.getName().equals(c.getName())) {
            System.out.println("Yes the customer is booked at that time");
        }
        return true;
    }

    // EFFECTS: returns true if the customer is booked at the booking time
    public boolean confirmBookedName(String cName, int bookingTime) {
        Customer bookedCustomer = bookings.get(bookingTime);
        String bookedCustomerName = bookedCustomer.getName();
        boolean isPersonBooked = bookedCustomerName.equals(cName);
        return isPersonBooked;
    }

    // MODIFIES: this and Customer
    // EFFECTS:  changes the customer booked in the bookings, and lets the Customer know the new booking time.
    public void changeBooking(Customer customer, int newTime) {
        int bookedTime = customer.getBookingTime();
        System.out.print(customer.getName() + "'s time is changing from " + bookedTime);
        System.out.println(" to " + newTime);
        bookings.set(bookedTime, null);
        bookings.set(newTime, customer);
        customer.setBookedTime(newTime);
    }


}