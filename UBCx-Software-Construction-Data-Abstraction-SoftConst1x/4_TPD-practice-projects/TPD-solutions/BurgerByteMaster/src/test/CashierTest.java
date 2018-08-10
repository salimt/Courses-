package test;

import model.BurgerByte;
import model.Cashier;
import model.Manager;
import org.junit.Before;
import org.junit.Test;

import static model.Cashier.CASHIER_WAGE;
import static model.Employee.BASE_WAGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CashierTest {

    private Manager manager;
    private BurgerByte restaurant;
    private Cashier cashier;

    @Before
    public void setUp() {
        manager = new Manager("Mr.Krabs", 30);
        restaurant = new BurgerByte("Bikini Bottom", manager);
        cashier = new Cashier("SquidWard Tentacles", 21, restaurant);
    }

    @Test
    public void testGetters() {
        assertEquals(cashier.getName(),"SquidWard Tentacles");
        assertEquals(cashier.getAge(), 21);
        assertEquals(cashier.getWorkPlace(), restaurant);
        assertFalse(cashier.isRegisterOpen());
        assertFalse(cashier.isAtWork());
    }

    @Test
    public void teststartWork() {
        assertFalse(cashier.isRegisterOpen());
        assertFalse(cashier.isAtWork());
        cashier.startWork(10.45);
        assertEquals(cashier.computeWage(),((CASHIER_WAGE + BASE_WAGE) * cashier.getHoursWorked()),  0.05);
        assertTrue(cashier.isRegisterOpen());
        assertTrue(cashier.isAtWork());
    }

    @Test
    public void testleaveWork() {
        cashier.startWork(4.50);
        assertTrue(cashier.isAtWork());
        assertTrue(cashier.isRegisterOpen());
        cashier.leaveWork();
        assertFalse(cashier.isAtWork());
        assertFalse(cashier.isRegisterOpen());
    }

    @Test
    public void testcomputeWage() {
        assertEquals(cashier.computeWage(), 0, 0.05);
        cashier.startWork(3.47);
        assertEquals(cashier.computeWage(),((CASHIER_WAGE + BASE_WAGE) * cashier.getHoursWorked()),  0.05);
    }


}