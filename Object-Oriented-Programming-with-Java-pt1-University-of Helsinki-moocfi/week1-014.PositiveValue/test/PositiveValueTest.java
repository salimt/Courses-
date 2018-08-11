
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Rule;
import org.junit.Test;

@Points("14")
public class PositiveValueTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testPositiivinenLuku(1);
    }

    @Test
    public void test2() {
        testPositiivinenLuku(0);
    }

    @Test
    public void test3() {
        testPositiivinenLuku(-1);
    }

    private void testPositiivinenLuku(int luku) {
        ReflectionUtils.newInstanceOfClass(PositiveValue.class);
        io.setSysIn(luku + "\n");
        PositiveValue.main(new String[0]);

        String out = io.getSysOut();

        assertTrue("You did not ask anything", out.trim().length() > 0);

        assertTrue("You should output \":\" when prompting user for input. You printed: " + out,
                out.contains(":"));


        if (luku > 0) {
            assertTrue("Your output should contain \"is positive\", when the given number is " +
                    luku + ", You printed: " + out,
                    out.contains("is positive"));
            assertFalse("Your output should not contain \"is not positive\", when the given number is " +
                    luku + ", You printed: " + out,
                    out.contains("is not positive"));
        } else {
            assertTrue("Your output should contain \"is not positive\", when the given number is " +
                    luku + ", You printed: " + out,
                    out.contains("is not positive"));
            assertFalse("Your output should not contain \"is positive\",when the given number is " +
                    luku + ", You printed: " + out,
                    out.contains("is positive"));
        }
    }
}
