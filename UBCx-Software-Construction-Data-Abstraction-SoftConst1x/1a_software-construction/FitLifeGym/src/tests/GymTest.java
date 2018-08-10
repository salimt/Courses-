package tests;

import model.Gym;
import model.cardio.Elliptical;
import model.cardio.IndoorBike;
import model.cardio.Treadmill;
import model.weights.Barbell;
import model.weights.Dumbbell;
import model.weights.Plate;
import model.weights.WeightMachine;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GymTest {

    private Gym gym;
    private Treadmill treadmill;
    private Elliptical elliptical;
    private IndoorBike bike;

    @Before
    public void setUp(){
        gym = new Gym();
        gym.setHasChildCare(true);
        gym.setHasPool(true);

        treadmill = new Treadmill(30, 5, 2.0);
        elliptical = new Elliptical(10, 1);
        bike = new IndoorBike(60, 10);

        loadWeights();
        loadMachines();
    }

    @Test
    public void testConstructor(){
        gym = new Gym();
        assertEquals(0, gym.getNumberOfCardioMachines());
        assertEquals(0, gym.getNumberOfWeights());
        assertFalse(gym.hasChildCare());
        assertFalse(gym.hasPool());
    }

    @Test
    public void testAddWeights(){
        assertEquals(gym.getNumberOfWeights(), 9);
        assertTrue(gym.getWeights().contains(new Plate(45)));
        assertFalse(gym.getWeights().contains(new Plate(50)));
    }

    @Test
    public void testAddMachines(){
        assertEquals(gym.getNumberOfCardioMachines(), 3);
        assertTrue(gym.getCardioMachines().contains(treadmill));
        assertTrue(gym.getCardioMachines().contains(elliptical));
    }

    @Test
    public void testRemoveWeights(){
        gym.removeWeight(new Plate(45));
        gym.removeWeight(new Dumbbell(15));
        assertEquals(gym.getNumberOfWeights(), 7);
    }

    @Test
    public void testCardioMachines(){
        gym.removeCardioMachine(treadmill);
        gym.removeCardioMachine(elliptical);
        assertEquals(gym.getNumberOfCardioMachines(), 1);
        assertFalse(gym.getCardioMachines().contains(elliptical));
        assertTrue(gym.getCardioMachines().contains(bike));
    }


    private void loadMachines(){
        gym.addCardioMachine(treadmill);
        gym.addCardioMachine(elliptical);
        gym.addCardioMachine(bike);
    }

    private void loadWeights(){
        gym.addWeight(new Barbell(100));
        gym.addWeight(new Plate(45));
        gym.addWeight(new Plate(45));
        gym.addWeight(new Plate(10));
        gym.addWeight(new Plate(10));
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new Dumbbell(15));
        gym.addWeight(new WeightMachine("quadriceps", 30));
        gym.addWeight(new WeightMachine("latissimus dorsi", 70));
    }


}