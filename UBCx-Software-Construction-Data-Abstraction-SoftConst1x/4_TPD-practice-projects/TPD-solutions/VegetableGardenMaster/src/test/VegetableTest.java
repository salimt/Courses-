package test;

import model.VegType;
import model.Veggies.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VegetableTest {

    private Carrot testCarrot;
    private Radish testRadish;
    private Tomato testTomato;
    private Lettuce testLettuce;
    private GreenBean testBean;

    @Before
    public void setUp() {
        testCarrot = new Carrot();
        testRadish = new Radish();
        testTomato = new Tomato();
        testLettuce = new Lettuce();
        testBean = new GreenBean();
    }

    @Test
    public void testCarrot() {
        assertEquals(testCarrot.getName(), "Carrot");
        assertEquals(testCarrot.getType(), VegType.ROOT);
        assertEquals(testCarrot.getInstructions(),
                "Plant 3-5 weeks before last spring frost");
    }

    @Test
    public void testRadish() {
        assertEquals(testRadish.getName(), "Radish");
        assertEquals(testRadish.getInstructions(),
                "Plant 4-6 weeks before last frost, in ground");
        assertEquals(testRadish.getType(), VegType.ROOT);
    }

    @Test
    public void testTomato() {
        assertEquals(testTomato.getType(), VegType.SEED);
        assertEquals(testTomato.getName(), "Tomato");
        assertEquals(testTomato.getInstructions(),
                "Start seeds indoors 6-8 weeks early");
    }

    @Test
    public void testGreenBean() {
        assertEquals(testBean.getName(), "Green Bean");
        assertEquals(testBean.getType(), VegType.SEED);
        assertEquals(testBean.getInstructions(), "");
    }

    @Test
    public void testLettuce() {
        assertEquals(testLettuce.getType(), VegType.LEAF);
        assertEquals(testLettuce.getName(), "Lettuce");
        assertEquals(testLettuce.getInstructions(), "");
    }


}