package tests;

import model.appliances.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ApplianceTest {
    private Oven testOven;
    private Refrigerator testFridge;
    private HeatingAC testHAC;
    private Fireplace testFireplace;
    private List<Appliance> appliances;

    @Before
    public void setUp(){
        testOven = new Oven(0);
        testFridge = new Refrigerator(5);
        testHAC = new HeatingAC(20);
        testFireplace = new Fireplace(8);
        appliances = new ArrayList<>();
        appliances.add(testOven);
        appliances.add(testFireplace);
        appliances.add(testFridge);
        appliances.add(testHAC);
    }

    @Test
    public void testConstructor(){
        assertEquals(0, testOven.getTemp());
        assertEquals(5, testFridge.getTemp());
        assertEquals(-5, testFridge.getFreezerTemp());
        assertEquals(20, testHAC.getTemp());
        assertEquals(8, testFireplace.getTemp());
        for(Appliance a: appliances){
            assertFalse(a.isRunning());
        }
    }

    @Test
    public void testRunsWhileAway(){
        assertTrue(testHAC.runsWhileAway());
        assertTrue(testFridge.runsWhileAway());
        assertFalse(testOven.runsWhileAway());
        assertFalse(testFireplace.runsWhileAway());
        testOven.setRunsWhileAway(true);
        assertTrue(testOven.runsWhileAway());
        testFireplace.setRunsWhileAway(true);
        assertTrue(testFireplace.runsWhileAway());
        testFireplace.setRunsWhileAway(false);
        assertFalse(testFireplace.runsWhileAway());
    }

    @Test
    public void testTurnOnOff(){
        testFireplace.turnOn();
        assertTrue(testFireplace.isRunning());
        testFireplace.turnOff();
        assertFalse(testFireplace.isRunning());
    }

    @Test
    public void testTemp(){
        testHAC.setTemp(28);
        assertEquals(28, testHAC.getTemp());
        testFridge.setFreezerTemp(-5);
        assertEquals(-5, testFridge.getFreezerTemp());
    }

    @Test
    public void testStatus(){
        assertEquals(testFridge.status(), "Refrigerator (Temp: 5)");
        assertEquals(testOven.status(), "Oven (Temp: 0)");
        assertEquals(testFireplace.status(), "Fireplace (Temp: 8)");
        assertEquals(testHAC.status(), "Heating AC (Temp: 20)");
    }

    @Test
    public void testMinMaxTemp(){
        assertEquals(testFridge.minTemp(), 0);
        assertEquals(testHAC.minTemp(), 15);
        assertEquals(testOven.minTemp(), 0);
        assertEquals(testFireplace.minTemp(), 0);

        assertEquals(testFridge.maxTemp(), 10);
        assertEquals(testOven.maxTemp(), 450);
        assertEquals(testHAC.maxTemp(), 30);
        assertEquals(testFireplace.maxTemp(), 10);
    }

    @Test
    public void testOvenSettings(){
        assertTrue(testOven.getMode());
        testOven.bake();
        assertTrue(testOven.getMode());
        testOven.broil();
        assertFalse(testOven.getMode());
    }

}
