
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("16")
public class EvenOrOddTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testParitonVaiParillinen(1);
    }

    @Test
    public void test2() {
        testParitonVaiParillinen(0);
    }

    @Test
    public void test3() {
        testParitonVaiParillinen(-1);
    }

    @Test
    public void aRealTest() {
        testParitonVaiParillinen(30);
    }
    @Test
    public void bRealTest() {
        testParitonVaiParillinen(29);
    }
    @Test
    public void cRealTest() {
        testParitonVaiParillinen(-11);
    }
    @Test
    public void dRealTest() {
        testParitonVaiParillinen(-12);
    }
    private void testParitonVaiParillinen(int luku) {
        ReflectionUtils.newInstanceOfClass(EvenOrOdd.class);

        io.setSysIn(luku + "\n");
        EvenOrOdd.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask anything", out.trim().length() > 0);

        assertTrue("You should output \":\" when prompting user for input. You printed: " + out,
                out.contains(":"));

        if (luku % 2 == 0) {
            assertTrue("Your output should contain \"is even\", when given number is " +
                    luku + ", You printed: " + out,
                    out.contains("even"));
            assertFalse("Your output should not contain \"is odd\", when given number is  " +
                    luku + ", You printed: " + out,
                    out.contains("odd"));
        } else {
            assertTrue("Your output should contain \"is odd\", when given number is  " +
                    luku + ", You printed: " + out,
                    out.contains("odd"));
            assertFalse("Your output should not contain \"is even\", when given number is  " +
                    luku + ", You printed: " + out,
                    out.contains("even"));
        }
    }
}
