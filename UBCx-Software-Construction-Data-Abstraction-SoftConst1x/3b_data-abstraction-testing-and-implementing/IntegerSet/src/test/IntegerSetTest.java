package test;

import model.IntegerSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntegerSetTest {

    IntegerSet testSet;

    @Before
    public void setUp() {
        testSet = new IntegerSet();
    }

    @Test
    public void testInsertNotThere() {
        checkSetEmptyDoesntContain(3);
        testSet.insert(3);
        checkSetContainsOnce(3);
    }

    @Test
    public void testInsertAlreadyThere() {
        checkSetEmptyDoesntContain(3);
        testSet.insert(3);
        checkSetContainsOnce(3);
        testSet.insert(3);
        checkSetContainsOnce(3);
    }

    private void checkSetContainsOnce(int num) {
        assertEquals(testSet.size(),1);
        assertTrue(testSet.contains(num));
    }

    private void checkSetEmptyDoesntContain(int num) {
        assertEquals(testSet.size(), 0);
        assertFalse(testSet.contains(num));
    }


}