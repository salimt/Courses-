import org.junit.Test;
import org.junit.Rule;

import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockStdio;

@Points("5")
public class SecondsOfTheYearTest {

    @Rule
    public MockStdio io = new MockStdio();

    @Test
    public void test() {
        SecondsOfTheYear.main(new String[0]);
        String out = io.getSysOut();

        assertTrue("You do not print anything!",out.trim().length()>0);
        assertTrue("Printed value is not correct!",out.contains("31536000"));
    }

}
