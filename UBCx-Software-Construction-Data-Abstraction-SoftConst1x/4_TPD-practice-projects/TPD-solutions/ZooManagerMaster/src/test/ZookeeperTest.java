package test;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZookeeperTest {

    private Zookeeper testKeeper;
    private Elephant testElephant;
    private Snake testSnake;
    private Whale testWhale;
    private Horse testHorse;

    @Before
    public void setUp() {
        testKeeper = new Zookeeper("John", 11);
        testElephant = new Elephant("Bob", "Cambodia", 4, testKeeper, 6000.00);
        testSnake = new Snake("Robert","Brazil", 3, testKeeper, 100.00, 12.1, true);
        testWhale = new Whale("Pearl", "Canada", 4, testKeeper, 10000.00, true, 10000.50);
        testHorse = new Horse("Bojack", "USA", 50, testKeeper, 500.00, 5.00);

        testKeeper.setFavourite(testSnake);
        testKeeper.addToList(testSnake);
        testKeeper.addToList(testElephant);
        testKeeper.addToList(testWhale);
        testKeeper.addToList(testHorse);
    }

    @Test
    public void testgetName() {
        assertEquals("John", testKeeper.getName());
    }

    @Test
    public void testgetAge(){
        assertEquals(11, testKeeper.getAge());
    }

    @Test
    public void testgetAnimalList() {
        assertEquals(4, testKeeper.getAnimalList().size());
        assertEquals(true, testKeeper.getAnimalList().contains(testElephant));
        assertEquals(true, testKeeper.getAnimalList().contains(testSnake));
        assertEquals(true, testKeeper.getAnimalList().contains(testWhale));
        assertEquals(true, testKeeper.getAnimalList().contains(testHorse));
    }

    @Test
    public void testremoveFromList() {
        assertEquals(true, testKeeper.removeFromList(testElephant));
        assertEquals(3, testKeeper.getAnimalList().size());
        assertEquals(false, testKeeper.removeFromList(testElephant));

        assertEquals(true, testKeeper.removeFromList(testSnake));
        assertEquals(2, testKeeper.getAnimalList().size());
        assertEquals(false, testKeeper.removeFromList(testSnake));

        assertEquals(true, testKeeper.removeFromList(testWhale));
        assertEquals(1, testKeeper.getAnimalList().size());
        assertEquals(false, testKeeper.removeFromList(testWhale));
    }


}