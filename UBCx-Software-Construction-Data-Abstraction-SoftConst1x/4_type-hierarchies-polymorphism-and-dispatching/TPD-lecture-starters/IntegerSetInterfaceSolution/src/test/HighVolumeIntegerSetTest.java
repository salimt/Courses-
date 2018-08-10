package test;

import model.HighVolumeIntegerSet;
import model.IntegerSet;
import model.LowVolumeIntegerSet;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HighVolumeIntegerSetTest extends IntegerSetTest{

    @Before
    public void setup(){
        testSet = new HighVolumeIntegerSet();
    }


    @Test
    public void testInsertHighVolume(){
        for (int i=0; i<50000; i++){
            testSet.insert(i);
            assertTrue(testSet.contains(i));
            assertEquals(testSet.size(), i+1);
        }
    }

}
