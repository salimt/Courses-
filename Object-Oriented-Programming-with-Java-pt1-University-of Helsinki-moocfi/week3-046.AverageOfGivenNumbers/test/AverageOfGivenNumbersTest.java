import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.powermock.api.easymock.PowerMock.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;

@Points("46")
@PrepareForTest(AverageOfGivenNumbers.class)
//@RunWith(PowerMockRunner.class)
public class AverageOfGivenNumbersTest {
    @Rule
    public PowerMockRule p = new PowerMockRule();
    

    @Test
    public void averageRight1() {
        assertEquals("Average not correct for values -12, 2, 8 and 0, check that you make correct type conversions",
                -0.5, AverageOfGivenNumbers.average(-12, 2, 8, 0), 0.0001);
    }

    @Test
    public void averageRight2() {
        assertEquals("Average not correct for values 1, 2, 3 and 4, check that you make correct type conversions",
                2.5, AverageOfGivenNumbers.average(1, 2, 3, 4), 0.0001);
    }
    
    @Test
    public void helperMethodUsed1() {
        mockStaticPartial(AverageOfGivenNumbers.class, "sum");
        reset(AverageOfGivenNumbers.class);

        AverageOfGivenNumbers.sum(1,2,3,4);
        expectLastCall().andReturn(10);

        replay(AverageOfGivenNumbers.class);       

        try {
            AverageOfGivenNumbers.average(1,2,3,4);
            verifyAll();
        } catch (AssertionError e) {
            fail("method average should use the method sum to calculate the sum of it's parameters"
                    + "\n"
                    + "the method sum should be called exactly once. "            
                    + "When called average(1,2,3,4) " + e);
        }

    }
        
    public void helperMethodUsed2() {
        mockStaticPartial(AverageOfGivenNumbers.class, "sum");
        reset(AverageOfGivenNumbers.class);

        AverageOfGivenNumbers.sum(4,2,3,7);
        expectLastCall().andReturn(16);

        replay(AverageOfGivenNumbers.class);       

        try {
            AverageOfGivenNumbers.average(4,2,3,7);
            verifyAll();
        } catch (AssertionError e) {
            fail("method average should use the method sum to calculate the sum of it's parameters"
                    + "\n"
                    + "the method sum should be called exactly once. "            
                    + "When called average(4,2,3,7) " + e);
        }

    }        
}
