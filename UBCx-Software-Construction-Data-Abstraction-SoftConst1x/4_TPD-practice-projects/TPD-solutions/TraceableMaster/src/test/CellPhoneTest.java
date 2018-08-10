package test;

import model.Call;
import model.CellPhone;
import model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellPhoneTest {

    private Person owner;
    private Call call;
    private CellPhone cell;

    @Before
    public void setUp() {
        owner = new Person("Anthony Estey", 29, "Victoria");
        cell = new CellPhone(owner, "Vancouver");
        call = new Call(cell, "Vancouver");
    }

    @Test
    public void testGetters() {
        assertEquals(cell.getCalls().size(), 0);
        assertEquals(cell.getOwner(), owner);
        assertEquals(cell.getLocation(), "Vancouver");
        assertEquals(cell.getTrace(), cell);
    }

    @Test
    public void addCall() {
        Call newCall = new Call(cell, "Seoul");
        assertEquals(cell.getCalls().size(), 0);
        cell.addCall(newCall);
        assertEquals(cell.getCalls().size(),1 );
        assertTrue(cell.getCalls().contains(newCall));
    }

    @Test
    public void testTrack() {
        call.track();
    }


}