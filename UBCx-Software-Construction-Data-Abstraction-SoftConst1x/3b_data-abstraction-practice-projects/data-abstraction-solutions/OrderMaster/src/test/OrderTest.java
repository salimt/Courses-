package test;

import model.Order;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderTest {

    private Order testOrder;
    private static final float TOL = 1f;

    @Before
    public void setUp(){
        testOrder = new Order(75, 'B', "Sazi");
    }

    @Test
    public void testConstructor(){
        assertEquals(testOrder.getCustName(), "Sazi");
        assertEquals(testOrder.getTicketNum(), 75);
        assertEquals(testOrder.getCombo(), 'B');
        assertEquals(testOrder.getPrice(), 9.5, TOL);
        assertFalse(testOrder.isCompleted());
    }

    @Test
    public void testOrderComplete(){
        testOrder.completed();
        //return getCustName() + " - " + getTicketNum() + " - Combo " + getCombo() + ": " + comboFoodType()
        //+ " - $" + getPrice();
        assertEquals("Sazi - 75 - Combo B: Pasta - $9.5", testOrder.customerReceipt());
    }

    @Test
    public void testOrderNotComplete(){
        assertFalse(testOrder.isCompleted());
        testOrder.setInstructions("Very al dente");
        assertEquals("75 COMBO B INSTRUCTIONS: Very al dente", testOrder.cookingInstructions());
    }


}
