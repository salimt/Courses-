package ui;

import model.Customer;
import model.HairSalon;

public class Main {

    public static void main(String[] args) {
        System.out.println("---------Create the hair salon--------------");
        HairSalon laBelleSalon = new HairSalon();

        //book someone into the salon
        System.out.println("---------Make and confirm booking-----------");
        Customer elisa = new Customer("Elisa");
        laBelleSalon.makeNewBooking(elisa, 12);
        laBelleSalon.verifyBooking(elisa, 12);
        elisa.confirmBooking();

        //change the booking for elisa
        System.out.println("---------Change a booking-------------------");
        laBelleSalon.changeBooking(elisa, 13);
        elisa.confirmBooking();
        laBelleSalon.verifyBooking(elisa, 13);

        //can we find people's bookings by name?
        System.out.println("---------Check booking by name---------------");
        System.out.println("Can we find Elisa by name? "+laBelleSalon.confirmBookedName("Elisa", 13));

        //print out all the bookings
        System.out.println("---------------------------------------------");
        System.out.println("All the bookings for the day:");
        System.out.println("---------------------------------------------");
        laBelleSalon.printBookingsList();
        System.out.println("---------------------------------------------");
    }


}