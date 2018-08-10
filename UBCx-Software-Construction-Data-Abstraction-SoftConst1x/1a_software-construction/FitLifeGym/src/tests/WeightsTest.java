package tests;

import org.junit.Before;
import org.junit.Test;
import model.weights.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WeightsTest {

    private Barbell barbell;
    private Dumbbell dumbbell;
    private Plate plate;
    private ResistanceBand band;
    private WeightMachine machine;

    @Before
    public void setUp(){
        barbell = new Barbell(100);
        dumbbell = new Dumbbell(25);
        plate = new Plate(10);
        band = new ResistanceBand(3);
        machine = new WeightMachine("hamstrings", 60);
    }

    @Test
    public void testConstructor(){
        assertEquals(barbell.getWeight(), 100);
        assertEquals(dumbbell.getWeight(), 25);
        assertEquals(plate.getWeight(), 10);
        assertEquals(machine.getWeight(), 60);
        assertEquals(band.getLevel(), 3);
    }

    @Test
    public void testSetWeight(){
        barbell.setWeight(135);
        assertEquals(barbell.getWeight(), 135);
        dumbbell.setWeight(10);
        assertEquals(dumbbell.getWeight(), 10);
        machine.setWeight(50);
        assertEquals(machine.getWeight(), 50);
    }

    @Test
    public void testDumbbell(){
        assertEquals(dumbbell.getTotalWeight(), 50);
    }

    @Test
    public void testPlate(){
        assertEquals(plate.getTotalWeight(), 20);
    }

    @Test
    public void testMachine(){
        assertEquals("hamstrings", machine.getMuscleGroup());
        machine = new WeightMachine("biceps", 30);
        assertEquals("biceps", machine.getMuscleGroup());
    }

    @Test
    public void testBand(){
        assertEquals(band.getColor(), "Purple");
        band = new ResistanceBand(1);
        assertEquals(band.getColor(), "Green");
        band = new ResistanceBand(2);
        assertEquals(band.getColor(), "Blue");
        band = new ResistanceBand(4);
        assertEquals(band.getColor(), "Yellow");
        band = new ResistanceBand(5);
        assertEquals(band.getColor(), "Red");
    }

    @Test
    public void testBarbellGetPlates1(){
        ArrayList<Plate> plates = new ArrayList<>();
        plates.add(new Plate(25));
        plates.add(new Plate(25));
        plates.add(new Plate(1));
        plates.add(new Plate(1));
        plates.add(new Plate(1));
        plates.add(new Plate(1));
        plates.add(new Plate(1));
        assertEquals(plates, barbell.getPlates());
    }

    @Test
    public void testBarbellGetPlates2(){
        barbell.setWeight(165);
        ArrayList<Plate> plates = new ArrayList<>();
        plates.add(new Plate(45));
        plates.add(new Plate(45));
        plates.add(new Plate(10));
        plates.add(new Plate(10));
        plates.add(new Plate(5));
        plates.add(new Plate(5));
        assertEquals(plates, barbell.getPlates());
    }


}