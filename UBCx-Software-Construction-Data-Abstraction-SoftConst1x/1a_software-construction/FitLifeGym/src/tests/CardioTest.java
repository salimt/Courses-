package tests;

import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CardioTest {

    private static final double TOL = 1.0f;

    private Elliptical elliptical;
    private IndoorBike bike;
    private Treadmill treadmill;

    @Before
    public void setUp(){
        elliptical = new Elliptical(30, 5);
        bike = new IndoorBike(20, 2);
        treadmill = new Treadmill(60, 1, 2.0);
    }

    @Test
    public void testConstructor(){
        assertEquals(30, elliptical.getMinutesRemaining());
        assertEquals(20, bike.getMinutesRemaining());
        assertEquals(60, treadmill.getMinutesRemaining());
        assertEquals(5, elliptical.getLevel());
        assertEquals(2, bike.getLevel());
        assertEquals(1, treadmill.getLevel());

        assertTrue(elliptical.hasIntervalSetting());
        assertFalse(bike.hasIntervalSetting());
        assertTrue(treadmill.hasIntervalSetting());
    }

    @Test
    public void testLevel(){
        elliptical.levelUp();
        assertEquals(6, elliptical.getLevel());
        treadmill.levelDown();
        assertEquals(1, treadmill.getLevel());
        bike.levelDown();
        assertEquals(1, bike.getLevel());
    }

    @Test
    public void testTreadmill(){
        assertEquals(2.0, treadmill.getSpeed(), TOL);
        treadmill.setSpeed(3.5);
        assertEquals(3.5, treadmill.getSpeed(), TOL);
        treadmill.setMinutes(20);
        assertEquals(20, treadmill.getMinutesRemaining());
    }


}