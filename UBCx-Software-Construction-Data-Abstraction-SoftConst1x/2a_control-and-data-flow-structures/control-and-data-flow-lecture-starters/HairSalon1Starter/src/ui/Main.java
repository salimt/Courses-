package ui;

import model.Customer;
import model.HairSalon;

public class Main {

    public static void main(String[] args) {
        HairSalon laBelleSalon = new HairSalon();

        // book someone into the salon
        Customer elisa = new Customer("Elisa");
        laBelleSalon.makeNewBooking(elisa, 12);
        laBelleSalon.verifyBooking(elisa, 12);
        laBelleSalon.printBookingsList();

        // book someone else into the salon
        Customer reid = new Customer("Reid");
        laBelleSalon.makeNewBooking(reid, 13);
        laBelleSalon.printBookingsList();
    }


}