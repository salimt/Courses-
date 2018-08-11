import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;

@Points("2")
public class GreeterTest {

    @Rule
    public MockStdio io = new MockStdio();

    public String[] oikein =
    {"Hello world!",
     "(And all the people of the world)"};

    @Test
    public void test() {
        Greeter.main(new String[0]);
        String out = io.getSysOut();
        assertTrue("You did not print anything!",out.length()>0);

        String[] lines = out.split("\n");

        assertEquals("Your program should print 2 lines, "
                + "so it should have 2 System.out.println() commands.",
                     oikein.length, lines.length);

        assertEquals("First line wrong",oikein[0],lines[0]);
        assertEquals("Second line wrong",oikein[1],lines[1]);
    }

}
