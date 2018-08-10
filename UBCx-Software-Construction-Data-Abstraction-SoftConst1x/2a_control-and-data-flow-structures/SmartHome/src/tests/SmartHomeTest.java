package tests;


import model.SmartHome;
import model.appliances.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SmartHomeTest {

    private SmartHome testHome;
    private Oven testOven;
    private Refrigerator testFridge;
    private HeatingAC testHAC;
    private Fireplace testFireplace;

    @Before
    public void setUp(){
        testHome = new SmartHome();

        testFridge = new Refrigerator(10);
        testFireplace = new Fireplace(0);
        testOven = new Oven(0);
        testHAC = new HeatingAC(25);

        testHome.install(testFridge);
        testHome.install(testFireplace);
        testHome.install(testHAC);
        testHome.install(testOven);
        testFridge.turnOn();
        testHAC.turnOn();
    }

    @Test
    public void testConstructor(){
        testHome = new SmartHome();
        assertEquals(0, testHome.getAllAppliances().size());
    }


    @Test
    public void testInstall(){
        testHome = new SmartHome();
        testHome.install(testOven);
        assertEquals(1, testHome.getAllAppliances().size());
        assertEquals(0, testHome.getAppliancesRunning().size());
        testHome.install(testFridge);
        testHome.install(testFireplace);
        testHome.install(testHAC);
        assertEquals(4, testHome.getAllAppliances().size());
        assertEquals(2, testHome.getAppliancesRunning().size());
    }

    @Test
    public void testRunningWhileAway(){
        List<Appliance> testList = new ArrayList<Appliance>(){
            {   add(testFridge);
                add(testHAC);   }
        };
        assertEquals(testList, testHome.getAppliancesRunning());
        testOven.setRunsWhileAway(true);
        testHome.leaveHome();
        assertEquals(3, testHome.getAppliancesRunning().size());
    }

    @Test
    public void testTurnOnAndOff(){
        testHome.turnOn(testOven);
        int index = testHome.getAppliancesRunning().indexOf(testOven);
        assertTrue(testHome.getAppliancesRunning().get(index).isRunning());
        testHome.setTemp(testOven, 350);
        assertEquals(350, testOven.getTemp());
        testHome.turnOff(testOven);
        testHome.turnOff(testFridge);
        testHome.turnOff(testHAC);
        assertEquals(0, testHome.getAppliancesRunning().size());
    }

    @Test
    public void testSetTemp(){
        testHome.setTemp(testFridge, 10);
        assertEquals(10, testFridge.getTemp());
        testHome.setTemp(testFireplace, 5);
        assertEquals(5, testFireplace.getTemp());
    }

    @Test
    public void testArriveLeaveHome(){
        assertFalse(testHome.isHome());
        testHome.arriveHome();
        assertTrue(testHome.isHome());
    }

    @Test
    public void testAllAppStatus(){
        StringBuilder status = new StringBuilder("");
        status.append("\nRefrigerator (Temp: 10)");
        status.append("\nHeating AC (Temp: 25)");

        assertEquals(2, testHome.getAppliancesRunning().size());
        assertEquals(status.toString(), testHome.appsRunningStatus());
        testHome.turnOn(testOven);
        status.append("\nOven (Temp: 0)");
        assertEquals(status.toString(), testHome.appsRunningStatus());
    }

    @Test
    public void testUpdate(){
        testHome.arriveHome();
        testHome.update();
        assertEquals(2, testHome.getAppliancesRunning().size());
        testHome.turnOn(testOven);
        testOven.setTemp(350);
        testHome.leaveHome();
        testHome.update();
        assertEquals(2, testHome.getAppliancesRunning().size());
    }

}
