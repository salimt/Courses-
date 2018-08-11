
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

@Points("11")
public class BiggerNumberTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testValues() {
        testSuurempiLuku(3, 7);
    }

    @Test
    public void testNegative() {
        testSuurempiLuku(-5, -3);
    }

    @Test
    public void testEqual() {
        testSuurempiLuku(3, 3);
    }

    @Test
    public void testGreaterFirst() {
	testSuurempiLuku(5, 1);
    }

    private void testSuurempiLuku(int eka, int toka) {
        ReflectionUtils.newInstanceOfClass(BiggerNumber.class);
        io.setSysIn(eka + "\n" + toka + "\n");
        BiggerNumber.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask anything!", out.trim().length() > 0);
        
        assertTrue("You should output \":\" when prompting user for input. You printed: "+out,
                   out.contains(":"));
        
        assertTrue("You should output text \"The bigger number of the two numbers given was:\". You printed: "+out,
                   out.contains("given was:"));
        

        String mjono = out.substring(out.indexOf("given was:") + "given was:".length());
        double suurempi = 0.0;
        try {
            suurempi = Double.parseDouble(mjono.trim());
        } catch (Exception e) {
            fail("Output your answer in following style \"The bigger number of the two numbers given was: 42\"");
        }

        int oikea = Math.max(eka, toka);
        assertEquals("Bigger of " + eka + " and " + toka + " should be " + oikea + ", you proposed: " + mjono.trim(), oikea, suurempi, 0.001);
    }
}
