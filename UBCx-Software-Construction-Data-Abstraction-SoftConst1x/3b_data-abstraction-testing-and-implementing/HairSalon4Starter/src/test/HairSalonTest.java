package test;

import model.Customer;
import model.HairSalon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HairSalonTest {

    private HairSalon laBelleSalon;
    private Customer elisa;

    @BeforeEach
    public void setUp(){
        laBelleSalon = new HairSalon();
        elisa = new Customer("Elisa");
    }


    @Test
    public void testMakeBookingAtAvailableTime(){
        assertTrue(laBelleSalon.makeNewBooking(elisa, 15));
        assertFalse(laBelleSalon.verifyBooking(elisa, 15));
        elisa.setBookedTime(15);
    }

    @Test
    public void makeMultipleBookingsOutOfOrder(){
        Customer bill11 = new Customer("Bill 11");
        boolean bill11Booked = laBelleSalon.makeNewBooking(bill11, 11);
        boolean bill11Verified = laBelleSalon.verifyBooking(bill11, 11);
        Customer bob10 = new Customer("Bob 10");
        boolean bob10Booked = laBelleSalon.makeNewBooking(bob10, 10);
        boolean bob10Verified = laBelleSalon.verifyBooking(bob10, 10);
        Customer sara9 = new Customer("Sara 9");
        boolean sara9Booked = laBelleSalon.makeNewBooking(sara9, 9);
        boolean sara9Verified = laBelleSalon.verifyBooking(sara9, 9);
        Customer jim15 = new Customer("Jim 15");
        boolean jim15Booked = laBelleSalon.makeNewBooking(jim15, 15);
        boolean jim15Verified = laBelleSalon.verifyBooking(jim15, 15);
        Customer sven16 = new Customer("bill");
        boolean sven16Booked = laBelleSalon.makeNewBooking(sven16, 16);
        boolean sven16Verified = laBelleSalon.verifyBooking(sven16, 16);

        assertTrue(bill11Booked);
        assertTrue(bob10Booked);
        assertTrue(sara9Booked);
        assertTrue(jim15Booked);
        assertTrue(sven16Booked);

        assertTrue(bill11Verified);
        assertTrue(bob10Verified);
        assertTrue(sara9Verified);
        assertTrue(jim15Verified);
        assertTrue(sven16Verified);
    }

    @Test
    public void makeMultipleBookingsOutOfOrderRefactored(){
        Customer bill11 = new Customer("Bill 11");
        boolean bill11Booked = laBelleSalon.makeNewBooking(bill11, 11);
        Customer bob10 = new Customer("Bob 10");
        boolean bob10Booked = laBelleSalon.makeNewBooking(bob10, 10);
        Customer sara9 = new Customer("Sara 9");
        boolean sara9Booked = laBelleSalon.makeNewBooking(sara9, 9);
        Customer jim15 = new Customer("Jim 15");
        boolean jim15Booked = laBelleSalon.makeNewBooking(jim15, 15);
        Customer sven16 = new Customer("bill");
        boolean sven16Booked = laBelleSalon.makeNewBooking(sven16, 16);
        assertTrue(bill11Booked);
        assertTrue(bob10Booked);
        assertTrue(sara9Booked);
        assertTrue(jim15Booked);
        assertTrue(sven16Booked);


        boolean bill11Verified = laBelleSalon.verifyBooking(bill11, 11);
        boolean bob10Verified = laBelleSalon.verifyBooking(bob10, 10);
        boolean sara9Verified = laBelleSalon.verifyBooking(sara9, 9);
        boolean jim15Verified = laBelleSalon.verifyBooking(jim15, 15);
        boolean sven16Verified = laBelleSalon.verifyBooking(sven16, 16);
        assertTrue(bill11Verified);
        assertTrue(bob10Verified);
        assertTrue(sara9Verified);
        assertTrue(jim15Verified);
        assertTrue(sven16Verified);
    }

    @Test public void confirmUnbookedTimeByName(){
        assertTrue(laBelleSalon.makeNewBooking(elisa, 15));
        assertTrue(laBelleSalon.verifyBooking(elisa, 15));
        // TODO: assertFalse(laBelleSalon.confirmBookedName("Elisa", 15));
    }

    @Test
    public void testMakeBookingAtTakenTime(){
        boolean madeBooking = laBelleSalon.makeNewBooking(elisa, 15);
        assertTrue(madeBooking);
        assertTrue(laBelleSalon.verifyBooking(elisa, 15));
        Customer c = new Customer ("Standin Customer");
        assertTrue(laBelleSalon.makeNewBooking(c, 15));
        assertTrue(laBelleSalon.verifyBooking(c, 15));
        assertFalse(laBelleSalon.verifyBooking(elisa, 15));
    }

    @Test
    public void testMakeEarliestBooking(){
        assertTrue(laBelleSalon.makeNewBooking(elisa, 9));
        assertTrue(laBelleSalon.verifyBooking(elisa, 9));
    }

    @Test
    public void testMakeLatestBooking(){
        assertTrue(laBelleSalon.makeNewBooking(elisa, 17));
        assertTrue(laBelleSalon.verifyBooking(elisa, 17));
    }


}