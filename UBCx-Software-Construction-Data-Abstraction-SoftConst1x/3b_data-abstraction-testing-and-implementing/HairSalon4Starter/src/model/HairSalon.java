package model;

import java.util.ArrayList;

public class HairSalon {

    private ArrayList<Customer> bookings;

    public HairSalon(){
        bookings = new ArrayList<>();
        for (int i = 0; i <= 17; i++) {
            // note to students: this is a for-loop
            // it will repeatedly run the statements in this code block
            // incrementing i each time
            // until i is equal to 18
            bookings.add(i, null);
        }
    }

    // MODIFIES: this and Customer
    // EFFECTS:  books the customer into the requested timeslot if it is a valid timeslot, and let's the Customer know the booking time.
    public boolean makeNewBooking(Customer c, int bookingTime) {
        if (bookingTime >= bookings.size()) {
            System.out.println("We can't process that booking time");
            return true;
        }
        System.out.println("Customer " + c.getName() + " has been booked at " + bookingTime);
        bookings.add(bookingTime, c);
        c.setBookedTime(bookingTime);
        return true;
    }

    // EFFECTS: prints out all the bookings. If the time has not been booked, prints "available"
    public void printBookingsList() {
        for (int i = 9; i < bookings.size(); i++) { // change this to be the wrong end value
            Customer c = bookings.get(i);
            if (c != null){
                System.out.print(i + "hrs: ");
                c.printName(); // This causes a bug because you can't access methods on a null value!
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
        if (bookedCustomer == null) {
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
        if (bookings.get(bookingTime) != null) {
            Customer bookedCustomer = bookings.get(bookingTime);
            String bookedCustomerName = bookedCustomer.getName();
            boolean isPersonBooked = bookedCustomerName.equals(cName);
            return isPersonBooked;
        }
        return false;
    }

    // MODIFIES: this and Customer
    // EFFECTS:  changes the customer booked in the bookings, and let's the Customer know the new booking time.
    public void changeBooking(Customer customer, int newTime) {
        int bookedTime = customer.getBookingTime();
        System.out.print(customer.getName() + "'s time is changing from " + bookedTime);
        System.out.println(" to "+ newTime);
        bookings.set(bookedTime, null);
        bookings.set(newTime, customer);
        customer.setBookedTime(newTime);
    }


}