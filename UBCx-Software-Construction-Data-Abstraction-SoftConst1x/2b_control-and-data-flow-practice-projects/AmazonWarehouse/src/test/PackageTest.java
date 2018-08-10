package test;

import model.Package;
import org.junit.Before;
import org.junit.Test;

import static model.PackageType.FOOD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PackageTest {

    private static final double DELTA = 0.05;

    private Package p1;

    @Before
    public void setUp() {
        p1 = new Package("Anthony Estey", "Victoria", FOOD, 22.1, true);
    }

    @Test
    public void testGetters() {
        assertEquals(p1.getOwner(), "Anthony Estey");
        assertEquals(p1.getCity(), "Victoria");
        assertEquals(p1.getType(), FOOD);
        assertEquals(p1.getWeight(), 22.1, DELTA);
        assertTrue(p1.isPrime());
    }


}