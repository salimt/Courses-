
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;

@Points("10")
public class CircumferenceTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void testYmpyranKehanPituus() {
        testYmpyranKehanPituus(3);
    }

    private void testYmpyranKehanPituus(int sade) {
        ReflectionUtils.newInstanceOfClass(Circumference.class);
        io.setSysIn(sade + "\n");
        Circumference.main(new String[0]);

        String out = io.getSysOut();

         assertTrue("You did not ask anything!", out.trim().length() > 0);

        assertTrue("You should output \":\" when prompting user for input. You printed: " + out,
                out.contains(":"));


        assertTrue("You should output text  \"Type the radius:\". You printed: " + out,
                out.contains("adius:"));

        String kehaMjono = out.substring(out.indexOf("circle:") + "circle:".length());
        double keha;
        try {
            keha = Double.parseDouble(kehaMjono.trim());
        } catch (Exception e) {
            fail("Output the result in following style \"Circumference of the circle: 125.1253\". You printed: \"" + kehaMjono + "\".");
            return;
        }

        double oikeatulos = (Math.PI * 2 * sade);
        assertEquals("Circumference of the circle with radius " + sade +
                " should be " + oikeatulos + ", you proposed: " +
                kehaMjono.trim(), oikeatulos, keha, 0.001);
    }
}
