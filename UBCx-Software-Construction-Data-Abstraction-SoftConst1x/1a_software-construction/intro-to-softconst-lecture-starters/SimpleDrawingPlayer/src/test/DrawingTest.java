package test;

import model.Drawing;
import model.Shape;
import org.junit.Before;
import org.junit.Test;
import sound.MidiSynth;

import java.awt.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class DrawingTest {

    private Drawing testDrawing;
    private Shape testShape1, testShape2;

    @Before
    public void setUp() {
        MidiSynth synth = new MidiSynth();
        synth.open();
        testDrawing = new Drawing();
        testShape1 = new Shape(new Point(1, 1), synth);
        testShape2 = new Shape(1, 3 , 5, 10);
    }

    @Test
    public void testGetters() {
        assertEquals(0, testDrawing.getShapes().size());
        testDrawing.setPlayLineColumn(5);
        assertEquals(5, testDrawing.getPlayLineColumn());
    }

    @Test
    public void testaddShape() {
        assertEquals(0, testDrawing.getShapes().size());
        testDrawing.addShape(testShape1);
        assertEquals(1, testDrawing.getShapes().size());
        assertTrue(testDrawing.containsShape(testShape1));
        testDrawing.addShape(testShape2);
        assertEquals(2, testDrawing.getShapes().size());
        assertTrue(testDrawing.containsShape(testShape1) && testDrawing.containsShape(testShape2));
    }

    @Test
    public void testremoveShape() {
        assertEquals(0, testDrawing.getShapes().size());
        testDrawing.addShape(testShape1);
        assertTrue(testDrawing.containsShape(testShape1));
        testDrawing.removeShape(testShape1);
        assertFalse(testDrawing.containsShape(testShape1));
        assertEquals(0, testDrawing.getShapes().size());
    }

    @Test
    public void testAddShapeStress() {
        MidiSynth synth = new MidiSynth();
        synth.open();
        int j = 0;
        for (int i = 0; i < 500000; i++) {
            testDrawing.addShape(new Shape(new Point(i, j), synth));
            j++;
        }
        assertEquals(500000, testDrawing.getShapes().size());
    }

    @Test
    public void addBigShape() {
        MidiSynth synth = new MidiSynth();
        synth.open();
        Shape bigShape = new Shape(new Point(1, 1),synth);
        bigShape.setBounds(new Point(1000000, 2000000));
        testDrawing.addShape(bigShape);
        assertTrue(testDrawing.containsShape(bigShape));
    }

    @Test
    public void addBigShapeStress() {
        MidiSynth synth = new MidiSynth();
        synth.open();
        int j = 0;

        for (int i = 0; i < 500000; i++) {
            Shape bigShape = new Shape(new Point(1, 1),synth);
            bigShape.setBounds(new Point(i * 10000, j * 200000));
            testDrawing.addShape(bigShape);
            j++;
        }
        assertEquals(500000, testDrawing.getShapes().size());
    }

    @Test
    public void testGetShapesAtPoint() {
        testShape1.setBounds(new Point(4, 2));
        testDrawing.addShape(testShape1);
        assertEquals(testShape1, testDrawing.getShapesAtPoint(new Point(1, 1)));
        assertEquals(null, testDrawing.getShapesAtPoint(new Point(1, 4)));
    }


}