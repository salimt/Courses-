package test;

import model.IntegerSet;
import model.LowVolumeIntegerSet;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LowVolumeIntegerSetTest extends IntegerSetTest {
    @Before
    public void setup(){
        testSet = new LowVolumeIntegerSet();
    }


}
