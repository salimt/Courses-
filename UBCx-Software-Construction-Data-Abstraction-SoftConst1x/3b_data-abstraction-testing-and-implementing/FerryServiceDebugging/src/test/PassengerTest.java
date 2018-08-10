package test;

import model.Passenger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class PassengerTest {

    private Passenger testPassenger;

    @Before
    public void setUp() {
        testPassenger = new Passenger("Bruce Wayne");
    }

    @Test
    public void testPassengerConstructor() {
        assertEquals(testPassenger.getName(), "Burce Wayne");
        assertEquals(testPassenger.getFerryCard().getOwner(), testPassenger);
    }

    @Test
    public void testPassengerChangeName() {
        testPassenger.setName("Batman");
        assertEquals("Batman",testPassenger.getName());
    }

}