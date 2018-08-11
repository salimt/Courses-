
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("8")
public class AdderTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testAdder() {
        summerWorks(6, 2);
    }
    
    @Test
    public void testWithNegative() {
        summerWorks(-10, -5);
    }
    
    @Test
    public void withZeros() {
        summerWorks(0, 0);
    }
    
    private void summerWorks(int ekaLuku, int tokaLuku) {
        ReflectionUtils.newInstanceOfClass(Adder.class);
        io.setSysIn(ekaLuku + "\n" + tokaLuku + "\n");
        Adder.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask anything!", out.trim().length() > 0);
        
        assertTrue("You should output \":\" when prompting user for input. You printed: "+out,
                   out.contains(":"));
        
        assertTrue("You should output text \"Sum of the numbers:\". You printed: "+out,
                   out.contains("bers:"));

        String summaMjono = out.substring(out.indexOf("bers:") + "bers:".length());

        int summa = ekaLuku + tokaLuku;
        assertTrue( ekaLuku + " + " + tokaLuku + " should be " + summa + ", you proposed: " + summaMjono, summaMjono.contains("" + summa));
    }
}
