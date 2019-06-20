package test;

import model.CoffeeMaker;
import org.junit.Before;
import org.junit.Test;
import model.exceptions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class CoffeeMakerTest {
    private CoffeeMaker testCM;

    @Before
    public void setUp(){
        testCM = new CoffeeMaker();
    }

    @Test
    public void testStartBothZero(){
        assertEquals(testCM.getTimeSinceLastBrew(), testCM.getCupsRemaining(), 0);
    }

    @Test
    public void testBrewPASS(){
        try {
            successfulBrew();
            testCM.brew(2.40, 14.8);
            testCM.brew(2.60, 15);
        } catch (Exception e) {
            fail();
        }
    }

    @Test (expected = NotEnoughBeansException.class)
    public void testNotEnoughBeans() throws BeansAmountException, WaterException {
        testCM.brew(2.39, 14.8);
    }

    @Test (expected = TooManyBeansException.class)
    public void testTooManyBeans() throws BeansAmountException, WaterException {
        testCM.brew(2.61, 14.8);
    }

    @Test (expected = WaterException.class)
    public void testNotEnoughWater() throws BeansAmountException, WaterException {
        testCM.brew(2.5, 14.75);
    }

    @Test (expected = WaterException.class)
    public void testBrewFailBothWrongWaterFirst() throws BeansAmountException, WaterException {
        testCM.brew(2.1, 14);
    }

    @Test
    public void testPourCoffeePASS() throws BeansAmountException, WaterException {
        successfulBrew();
        try {
            testCM.pourCoffee();
            testCM.pourCoffee();
            testCM.pourCoffee();
            testCM.pourCoffee();
            testCM.pourCoffee();
            assertEquals(testCM.getCupsRemaining(), 15);
        } catch (Exception e) {
            fail();
        }
    }

    @Test (expected = StaleCoffeeException.class)
    public void testFailStale() throws BeansAmountException, WaterException, NoCupsRemainingException, StaleCoffeeException {
        successfulBrew();
        testCM.setTimeSinceLastBrew(60);
        testCM.pourCoffee();
    }

    @Test (expected = NoCupsRemainingException.class)
    public void testNoCupsFAIL() throws BeansAmountException, StaleCoffeeException, WaterException, NoCupsRemainingException {
        fill20Cups();
        testCM.pourCoffee();
    }

    @Test (expected = NoCupsRemainingException.class)
    public void testNoCupsWithStaleCoffee() throws BeansAmountException, StaleCoffeeException, WaterException, NoCupsRemainingException {
        fill20Cups();
        testCM.setTimeSinceLastBrew(60);
        testCM.pourCoffee();
    }

    private void fill20Cups() throws BeansAmountException, WaterException, NoCupsRemainingException, StaleCoffeeException {
        successfulBrew();
        for(int i=0; i<20; i++){
            testCM.pourCoffee();
        }
    }

    private void successfulBrew() throws BeansAmountException, WaterException {
        testCM.brew(2.5, 14.8);
    }
}
