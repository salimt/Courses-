package test;

import model.Ferry;
import model.Passenger;
import model.Ticket;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FerryTest {

    private Ferry testFerry;
    private int testPrice;
    private Date testDate;

    @Before
    public void setUp() {
        testPrice = 20;
        testDate = new Date();
        testFerry = new Ferry("Nanaimo", testDate, testPrice);
    }

    @Test
    public void testFerryConstructor() {
        assertEquals(testFerry.getDestination(), "Nanaimo");
        assertEquals(testFerry.getSailingDate(), testDate);
        assertEquals(testFerry.getTicketPrice(), testPrice);
        assertTrue(testFerry.getTicketSet().isEmpty());
    }

    @Test
    public void testAddTicket() {
        Passenger testPassenger = new Passenger("Bruce Wayne");
        Ticket testTicket = new Ticket(testFerry, testPassenger);
        testFerry.addTicket(testTicket);
    }

    @Test
    public void testChangeItinerary() {
        String newDest = "Juneau";
        testFerry.setDestination(newDest);
        Ferry comparison = new Ferry(newDest,testDate,testPrice);
        assertEquals(comparison.getDestination(),testFerry.getDestination());
        assertEquals(comparison.getSailingDate(),testFerry.getSailingDate());
        assertEquals(comparison.getTicketPrice(),testFerry.getTicketPrice());
    }


}