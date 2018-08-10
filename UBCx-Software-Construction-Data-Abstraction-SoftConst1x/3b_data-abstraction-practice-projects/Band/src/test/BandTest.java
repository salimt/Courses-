package test;

import model.Band;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BandTest {

    private Band band;

    @Before
    public void setUp() {
        band = new Band("April Fools Childrenhood");
    }

    @Test
    public void testConstructor() {
        assertEquals(band.getName(), "April Fools Childrenhood");
        assertEquals(band.getMembers().size(), 0);
        assertEquals(band.getTotalMoney(), 0, 0.01);
        assertEquals(band.getCurrentMoney(), 0, 0.01);
        assertEquals(band.getNumShowsPlayed(), 0);
    }

    @Test
    public void testAddMembers() {
        band.addMember("David Cowling");
        assertEquals(band.getMembers().size(), 1);

        band.addMember("Emma Citrine");
        assertEquals(band.getMembers().size(), 2);
        assertTrue(band.getMembers().contains("David Cowling"));
        assertTrue(band.getMembers().contains("Emma Citrine"));
        assertFalse(band.getMembers().contains("Erika Thompson"));
    }

    @Test
    public void testPlayGig() {
        band.playGig(100);
        assertEquals(band.getTotalMoney(), 100, 0.01);
        assertEquals(band.getCurrentMoney(), 100, 0.01);

        band.playGig(175.50);
        assertEquals(band.getTotalMoney(), 275.50, 0.01);
        assertEquals(band.getCurrentMoney(), 275.50, 0.01);
    }

    @Test
    public void testPayMembers() {
        band.addMember("Raeanne");
        band.playGig(100);
        band.payMembers(20);
        assertEquals(band.getCurrentMoney(), 80, 0.01);

        band.addMember("Sazi");
        band.payMembers(50);
        assertEquals(band.getCurrentMoney(), 80, 0.01);

        band.payMembers(30);
        assertEquals(band.getCurrentMoney(), 20, 0.01);
    }

    @Test
    public void testAverageMoney() {
        band.playGig(100);
        band.playGig(25.75);
        band.playGig(33.33);
        band.playGig(145);
        assertEquals(band.averagePerShow(), (100+25.75+33.33+145)/4, 0.01);
    }


}
