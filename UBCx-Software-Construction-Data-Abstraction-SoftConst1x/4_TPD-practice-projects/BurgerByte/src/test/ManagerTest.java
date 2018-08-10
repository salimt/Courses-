package test;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static model.Employee.BASE_WAGE;
import static model.Manager.MANAGER_WAGE;
import static org.junit.Assert.*;

public class ManagerTest {

    private BurgerByte restaurant;
    private Manager manager;
    private FryCook frycook;
    private Cashier cashier;

    @Before
    public void setUp() {
        manager = new Manager("Mr.Krabs", 30);
        restaurant = new BurgerByte("Bikini Bottom", manager);
        manager.setManagingBranch(restaurant);
        frycook = new FryCook("SpongeBob SquarePants", 20, restaurant);
        cashier = new Cashier("SquidWard Tentacles", 21, restaurant);
    }

//    @Test
//    public void testGetters() {
//        assertEquals(manager.getManagingBranch(), restaurant);
//        assertEquals(manager.getTeam().size(), 2);
//        assertTrue(manager.getTeam().contains(frycook));
//        assertTrue(manager.getTeam().contains(cashier));
//        assertFalse(manager.isAtWork());
//        assertEquals(manager.getAge(), 30);
//    }

//    @Test
//    public void testfire() {
//        assertEquals(manager.getTeam().size(), 2);
//        assertEquals(manager.getManagingBranch().getStaff().size(), 2);
//        manager.fire(frycook);
//        assertEquals(manager.getTeam().size(), 1);
//        assertEquals(manager.getManagingBranch().getStaff().size(), 1);
//        assertFalse(manager.getTeam().contains(frycook));
//        assertFalse(manager.getManagingBranch().getStaff().contains(frycook));
//    }

//    @Test
//    public void testhire() {
//        assertEquals(manager.getTeam().size(), 2);
//        FryCook newHire = new FryCook("Patrick Star", 19, restaurant);
//        manager.fire(newHire);
//        assertEquals(manager.getTeam().size(), 2);
//        assertEquals(manager.getManagingBranch().getStaff().size(), 2);
//        manager.hire(newHire);
//        assertEquals(manager.getTeam().size(), 3);
//        assertEquals(manager.getManagingBranch().getStaff().size(), 3);
//    }

    @Test
    public void teststartWork() {
        assertFalse(manager.getManagingBranch().isOpen());
        assertFalse(manager.isAtWork());
        manager.startWork(10.33);
        assertTrue(manager.getManagingBranch().isOpen());
        assertTrue(manager.isAtWork());
    }

    @Test
    public void testleaveWork() {
        manager.startWork(10);
        assertTrue(manager.getManagingBranch().isOpen());
        assertTrue(manager.isAtWork());
        frycook.startWork(10);
        cashier.startWork(9);
        assertTrue(frycook.isAtWork());
        assertTrue(cashier.isAtWork());
        manager.leaveWork();
        assertFalse(manager.getManagingBranch().isOpen());
        assertFalse(manager.isAtWork());
        assertFalse(frycook.isAtWork());
        assertFalse(cashier.isAtWork());
    }

    @Test
    public void testcomputeWages() {
        assertEquals(manager.computeWage(), 0, 0.05);
        manager.startWork(4.35);
        assertEquals(manager.computeWage(), (BASE_WAGE + MANAGER_WAGE) * 4.35, 0.06);
    }






}