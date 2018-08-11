
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;

@Points("15")
public class AgeOfConsentTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        testTaysiIkaisyys(17);
    }

    @Test
    public void test2() {
        testTaysiIkaisyys(18);
    }

    @Test
    public void test3() {
        testTaysiIkaisyys(19);
    }

    private void testTaysiIkaisyys(int luku) {
        ReflectionUtils.newInstanceOfClass(AgeOfMajority.class);
        io.setSysIn(luku + "\n");
        AgeOfMajority.main(new String[0]);

        String out = io.getSysOut();


        assertTrue("You did not ask anything", out.trim().length() > 0);

        assertTrue("You should output \"?\" when prompting user for input. You printed: " + out,
                out.contains("?"));

        String viesti = "With age "+luku+", ";
        if (luku >= 18) {
            assertTrue(viesti+"Your output should contain \"You have reached the age of majority!\". You printed: " + out,
                    out.contains("ve re"));
            assertTrue(viesti+"Your output should not contain \"You have not reached the age of majority yet!\". You printed: "  + out,
                    !out.contains("ve not re"));
        } else {
            assertTrue("Your output should contain \"You have not reached the age of majority yet!\". You printed: " + out,
                    out.contains("ve not re"));
            assertTrue(viesti+"Your output should not contain \"You have reached the age of majority!\". You printed: "  + out,
                    !out.contains("ve re"));
        }
    }
}
