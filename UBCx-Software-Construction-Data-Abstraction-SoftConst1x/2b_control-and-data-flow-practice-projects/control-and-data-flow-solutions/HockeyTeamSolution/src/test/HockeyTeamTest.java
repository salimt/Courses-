package test;

import model.Player;
import org.junit.Before;
import org.junit.Test;
import util.HockeyTeam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HockeyTeamTest {

    private HockeyTeam hockeyTeam;
    private Player p1, p2, p3, p4, p5, p6;

    @Before
    public void setUp() {
        hockeyTeam = new HockeyTeam();
        p1 = new Player("Wayne Gretzky", 99);
        p2 = new Player("Henrik Sedin", 33);
        p3 = new Player("Wayne Gretzky", 99);
        p4 = new Player("John Paulson", 2);
        p5 = new Player("Sidney Crosby", 87);
        p6 = new Player("Jack Hammerschmidt", 45);
    }

    @Test
    public void testinsert() {
        hockeyTeam.insert(p1);
        assertEquals(hockeyTeam.size(), 1);
        hockeyTeam.insert(p1);
        assertEquals(hockeyTeam.size(), 1);
        hockeyTeam.insert(p2);
        hockeyTeam.insert(p3);
        assertEquals(hockeyTeam.size(), 2);
    }

    @Test
    public void testretrieve() {
        hockeyTeam.insert(p1);
        hockeyTeam.insert(p2);
        hockeyTeam.insert(p3);
        assertEquals(hockeyTeam.retrieve(11), null);
        assertEquals(hockeyTeam.retrieve(99), p1);
        assertEquals(hockeyTeam.retrieve(33), p2);
    }

    @Test
    public void testremove() {
        hockeyTeam.insert(p4);
        hockeyTeam.insert(p5);
        hockeyTeam.insert(p6);
        assertTrue(hockeyTeam.contains(p4));
        assertTrue(hockeyTeam.contains(p5));
        assertTrue(hockeyTeam.contains(p6));
        hockeyTeam.remove(2);
        assertFalse(hockeyTeam.contains(p4));
    }

    @Test
    public void testsize() {
        assertEquals(hockeyTeam.size(), 0);
        hockeyTeam.insert(p1);
        assertEquals(hockeyTeam.size(), 1);
        hockeyTeam.insert(p1);
        assertEquals(hockeyTeam.size(), 1);
        hockeyTeam.insert(p2);
        assertEquals(hockeyTeam.size(), 2);

        hockeyTeam.printRoster();
    }

    @Test
    public void testintersection() {
        HockeyTeam otherTeam = new HockeyTeam();
        hockeyTeam.insert(p1);
        otherTeam.insert(p1);
        hockeyTeam.insert(p2);
        otherTeam.insert(p3);
        otherTeam.insert(p2);

        assertEquals(hockeyTeam.intersection(otherTeam).size(), 2);
        assertTrue(hockeyTeam.intersection(otherTeam).contains(p1));
        assertTrue(hockeyTeam.intersection(otherTeam).contains(p2));
        assertFalse(hockeyTeam.intersection(otherTeam).contains(p3));
    }


}