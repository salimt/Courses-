package test;

import model.BurgerByte;
import model.FryCook;
import model.Manager;
import org.junit.Before;
import org.junit.Test;

import static model.Employee.BASE_WAGE;
import static model.FryCook.FRYCOOK_WAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FryCookTest {

    private Manager manager;
    private BurgerByte restaurant;
    private FryCook frycook;

    @Before
    public void setUp() {
        manager = new Manager("Mr.Krabs", 30);
        restaurant = new BurgerByte("Bikini Bottom", manager);
        frycook = new FryCook("SpongeBob SquarePants", 20, restaurant);
    }

    @Test
    public void testGetters() {
        assertEquals(frycook.getName(),"SpongeBob SquarePants");
        assertEquals(frycook.getAge(),20);
        assertEquals(restaurant, frycook.getWorkPlace());
        assertFalse(frycook.isGrillReady());
        assertFalse(frycook.isAtWork());
    }

    @Test
    public void teststartWork() {
        assertFalse(frycook.isAtWork());
        assertFalse(frycook.isGrillReady());
        frycook.startWork(4.31);
        assertTrue(frycook.isGrillReady());
        assertTrue(frycook.isGrillReady());
    }

    @Test
    public void testleaveWork() {
        frycook.startWork(3.44);
        assertTrue(frycook.isGrillReady());
        assertTrue(frycook.isAtWork());
        frycook.leaveWork();
        assertFalse(frycook.isGrillReady());
        assertFalse(frycook.isAtWork());
    }

    @Test
    public void testcomputeWage() {
        assertEquals(frycook.computeWage(), 0, 0.05);
        frycook.startWork(3.14);
        assertEquals(frycook.computeWage(), ((FRYCOOK_WAGE + BASE_WAGE) * frycook.getHoursWorked()), 0.05);
    }

}