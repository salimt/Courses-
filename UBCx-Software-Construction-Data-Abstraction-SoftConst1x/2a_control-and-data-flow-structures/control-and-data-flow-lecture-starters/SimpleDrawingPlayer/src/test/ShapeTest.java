package test;

import model.Shape;
import org.junit.Before;
import org.junit.Test;
import sound.MidiSynth;

import java.awt.*;

import static org.junit.Assert.*;

public class ShapeTest {

    private Shape testShape1, testShape2;
    private MidiSynth synth;

    @Before
    public void setUp() {
        synth = new MidiSynth();
        synth.open();
        testShape1 = new Shape(new Point(1, 1), synth);
        testShape2 = new Shape(1, 3 , 5, 10);
    }

    @Test
    public void testGetters() {
        assertEquals(1, testShape2.getXCoord());
        assertEquals(3, testShape2.getYCoord());
        assertEquals(5, testShape2.getWidth());
        assertEquals(10, testShape2.getHeight());

        assertEquals(1, testShape1.getXCoord());
        assertEquals(1, testShape1.getYCoord());
    }

    @Test
    public void testsetBounds() {
        // Start in Quadrant I
        assertEquals(0, testShape1.getHeight());
        assertEquals(0, testShape1.getWidth());

        testShape1.setBounds(new Point(4, 5));

        assertEquals(4, testShape1.getHeight());
        assertEquals(3, testShape1.getWidth());
    }

    @Test
    public void testSetBoundsGeneral() {
        assertEquals(10, testShape2.getHeight());
        assertEquals(5, testShape2.getWidth());

        testShape2.setBounds(new Point(4, 7));

        assertEquals(4, testShape2.getHeight());
        assertEquals(3, testShape2.getWidth());
    }

    @Test
    public void testSelectAndPlay() {
        testShape1.setBounds(new Point(5, 4));
        assertFalse(testShape1.isSelected());
        testShape1.selectAndPlay();
        assertTrue(testShape1.isSelected());
    }

    @Test
    public void testUnselectAndStop() {
        testShape1.setBounds(new Point (10, 3));
        assertFalse(testShape1.isSelected());
        testShape1.unselectAndStopPlaying();
        assertFalse(testShape1.isSelected());
        testShape1.selectAndPlay();
        assertTrue(testShape1.isSelected());
        testShape1.unselectAndStopPlaying();
        assertFalse(testShape1.isSelected());
    }

    @Test
    public void testcontainsX() {
        testShape1.setBounds(new Point(4, 2));
        assertFalse(testShape1.containsX(0));
        assertFalse(testShape1.containsX(5));
        assertTrue(testShape1.containsX(3));
    }

    @Test
    public void testcontainsPoint() {
        testShape1.setBounds(new Point(5, 5));
        assertTrue(testShape1.contains(new Point(5, 5)));
        assertFalse(testShape1.contains(new Point(0, 0)));
        assertTrue(testShape1.contains(new Point(4, 4)));
        assertTrue(testShape1.contains(new Point(4, 1)));
        assertTrue(testShape1.contains(new Point(1, 1)));
        assertFalse(testShape1.contains(new Point(5, 7)));
        assertFalse(testShape1.contains(new Point(8, 8)));
        assertTrue(testShape1.contains(new Point(1, 1)));
    }

    @Test
    public void testmove() {
        testShape1.setBounds(new Point(4, 2));
        assertEquals(3, testShape1.getWidth());
        assertEquals(1, testShape1.getHeight());

        testShape1.move(3, 4);

        assertEquals(1 + 3, testShape1.getXCoord());
        assertEquals(1 + 4, testShape1.getYCoord());
        assertEquals(3, testShape1.getWidth());
        assertEquals(1, testShape1.getHeight());

    }

    @Test
    public void testmoveHugeDistance() {
        testShape1.setBounds(new Point(4, 2));
        assertEquals(3, testShape1.getWidth());
        assertEquals(1, testShape1.getHeight());

        testShape1.move(10000, 20000);

        assertEquals(1 + 10000, testShape1.getXCoord());
        assertEquals(1 + 20000, testShape1.getYCoord());
        assertEquals(3, testShape1.getWidth());
        assertEquals(1, testShape1.getHeight());
    }


}