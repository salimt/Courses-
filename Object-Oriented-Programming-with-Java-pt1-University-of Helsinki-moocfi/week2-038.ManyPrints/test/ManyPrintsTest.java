
import org.junit.Test;
import org.junit.Rule;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.MockInOut;

import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import static org.powermock.api.easymock.PowerMock.*;

import java.lang.reflect.Field;
import org.powermock.modules.junit4.rule.PowerMockRule;


@PrepareForTest(ManyPrints.class)
@Points("38")
public class ManyPrintsTest {

    @Rule
    public PowerMockRule p = new PowerMockRule();

    @Test
    public void noVariables() {
        Field[] fs = ManyPrints.class.getDeclaredFields();
        if (fs.length != 0) {
            fail("You have variable " + fs[0].getName() + " in the class ManyPrints, please remove that");
        }
    }

    @Test
    public void testMethod() {
        MockInOut mio = new MockInOut("");
        try {
            ManyPrints.printText();
            assertEquals("You did not print the right string int the method printText()", 
                    "In the beginning there were the swamp, the hoe and Java.", mio.getOutput().trim());
        } catch (Throwable t) {
            fail("Something went wrong in method printText(). Ensure that method prints only the string \"In the beginning there were the swamp, the hoe and Java.\". "
                    + "More info " + t);
        }
        mio.close();
    }

    @Test
    public void printsSomething() {
        MockInOut mio = new MockInOut("3\n\n");
        ManyPrints.main(null);
        assertFalse("You did not print anything", mio.getOutput().isEmpty());
        mio.close();
    }

    @Test
    public void printsOneRight() throws Exception {
        MockInOut mio = new MockInOut("1\n");

        ManyPrints.main(null);

        String out = mio.getOutput();
        String[] rivit = out.split("\n");
        assertEquals("You did not print the right string to prompt the user",
                "How many?", rivit[0].trim());
        assertEquals("You printed wrong number of lines with input \"1\".", 2, rivit.length);
        assertEquals("You did not priny the right string",
                "In the beginning there were the swamp, the hoe and Java.",
                rivit[1].trim());
        mio.close();
    }

    @Test
    public void printsManyRight() throws Exception {
        MockInOut mio = new MockInOut("9\n");

        ManyPrints.main(null);

        String out = mio.getOutput();
        String[] rivit = out.split("\n");
         assertEquals("You did not print the right string to prompt the user",
                "How many?", rivit[0].trim());
        assertEquals("You printed wrong number of lines with input \"9\".", 10, rivit.length);

        for (int i = 1; i < rivit.length; i++) {
            assertEquals("You did not priny the right string",
                "In the beginning there were the swamp, the hoe and Java.",
                    rivit[i].trim());
        }

        mio.close();
    }

    @Test
    public void callsTheMethod() throws Exception {
        MockInOut mio = new MockInOut("2\n");
        
        mockStaticPartial(ManyPrints.class, "printText");
        ManyPrints.printText();
        ManyPrints.printText();

        replay(ManyPrints.class);

        ManyPrints.main(null);

        try {
            verifyAll();
        } catch (AssertionError e) {
            fail("You did not call the method printText() twice with the input 2. "
                    + "Use the method printText() to print the string \"In the beginning...\". More info " + e);
        }
        mio.close();
    }

}
