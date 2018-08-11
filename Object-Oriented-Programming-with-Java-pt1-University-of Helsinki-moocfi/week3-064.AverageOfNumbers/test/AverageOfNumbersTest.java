
import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import static org.powermock.api.easymock.PowerMock.*;
import org.powermock.modules.junit4.PowerMockRunner;

@Points("64")
@PrepareForTest(AverageOfNumbers.class)
//@RunWith(PowerMockRunner.class)
public class AverageOfNumbersTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    public ArrayList<Integer> a(int... i) {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (int j : i) {
            al.add(j);
        }
        return al;
    }

    @Test
    public void test1() {
        assertEquals("The average of list [1] is not correct", 1, AverageOfNumbers.average(a(1)), 0.0001);
        assertEquals("The average of list [2] is not correct", 2, AverageOfNumbers.average(a(2)), 0.0001);
        assertEquals("The average of list [3] is not correct", 3, AverageOfNumbers.average(a(3)), 0.0001);
    }

    @Test
    public void test2() {
        assertEquals("The average of list [1,2,3] is not correct",
                2, AverageOfNumbers.average(a(1, 2, 3)), 0.0001);
        assertEquals("The average of list [2,2,2,2,2,2,2] is not correct",
                2, AverageOfNumbers.average(a(2, 2, 2, 2, 2, 2, 2)), 0.0001);
        assertEquals("The average of list [-1,1,-2,2,-3,3] is not correct",
                0, AverageOfNumbers.average(a(-1, 1, -2, 2, -3, 3)), 0.0001);
        assertEquals("The average of list [1,1,1,1,2,2,2] is not correct",
                1.429, AverageOfNumbers.average(a(1, 1, 1, 1, 2, 2, 2)), 0.001);
    }

    @Test
    public void testUseOfMethodSum() {
        mockStaticPartial(AverageOfNumbers.class, "sum");
        reset(AverageOfNumbers.class);

        ArrayList<Integer> luvut = new ArrayList<Integer>();
        luvut.add(1);
        luvut.add(3);


        AverageOfNumbers.sum(luvut);
        expectLastCall().andReturn(4);

        replay(AverageOfNumbers.class);

        AverageOfNumbers.average(luvut);
        
        try {
            verifyAll();
        }  catch (AssertionError e) {
            fail("method average should use method sum to calculate the sum of it's parameter\n"
                    + "When average was called to list [1,3] " + e);
        }

    }
    
    @Test
    public void testaaApumetodinKaytto2() {
        mockStaticPartial(AverageOfNumbers.class, "sum");
        reset(AverageOfNumbers.class);

        ArrayList<Integer> luvut = new ArrayList<Integer>();
        luvut.add(1);
        luvut.add(9);
        luvut.add(3);
        luvut.add(4);

        AverageOfNumbers.sum(luvut);
        expectLastCall().andReturn(17);

        replay(AverageOfNumbers.class);

        AverageOfNumbers.average(luvut);
        
        try {
            verifyAll();
        }  catch (AssertionError e) {
            fail("method average should use method sum to calculate the sum of it's parameter\n"
                    + "When average was called to list[1,9,3,4] " + e);
        }    
    }
}
 
