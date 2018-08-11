
import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;

@Points("3")
public class SpruceTest {

    @Rule
    public MockStdio io = new MockStdio();
    public String[] oikein = {"    *",
        "   ***",
        "  *****",
        " *******",
        "*********",
        "    *"};

    @Test
    public void test() {
        Spruce.main(new String[0]);
        String out = io.getSysOut();
        assertTrue("You did not print any stars!", out.contains("*"));

        String[] lines = out.split("\n");

        assertEquals("Your program should print 6 lines, "
                + "so it should have 6 System.out.println() commands.",
                oikein.length, lines.length);

        assertEquals("First line wrong", oikein[0], lines[0]);
        assertEquals("Second line wrong", oikein[1], lines[1]);
        assertEquals("Third line wrong", oikein[2], lines[2]);
        assertEquals("Fourth line wrong", oikein[3], lines[3]);
        assertEquals("Fifth line wrong", oikein[4], lines[4]);
        assertEquals("Sixth line wrong", oikein[5], lines[5]);

    }
}
