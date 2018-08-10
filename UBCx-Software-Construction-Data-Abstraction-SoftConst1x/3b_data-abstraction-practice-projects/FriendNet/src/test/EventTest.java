package test;

import model.Event;
import org.junit.Before;
import org.junit.Test;

import static model.EventType.*;
import static org.junit.Assert.*;

public class EventTest {

    private Event ev1;
    private Event ev2;
    private Event ev3;
    private Event ev4;

    @Before
    public void setUp() {
        ev1 = new Event("John's Wedding", "May 20", "Stanley Park", WEDDING);
        ev2 = new Event("Celina's Party", "May 11", "2298 West 4th Avenue", PARTY);
        ev3 = new Event("Anthony's Birthday", "August 29", "10109 University Blvd", BIRTHDAY);
        ev4 = new Event("Bill's Lecture", "May 4", "2366 Main Mall", SCHOOL);
    }

    @Test
    public void testGetters() {
        assertEquals(ev1.getName(), "John's Wedding");
        assertEquals(ev1.getEventType(), WEDDING);
        assertEquals(ev2.getName(), "Celina's Party");
        assertEquals(ev2.getEventType(), PARTY);
        assertEquals(ev3.getEventType(), BIRTHDAY);
        assertEquals(ev4.getEventType(), SCHOOL);
    }


}